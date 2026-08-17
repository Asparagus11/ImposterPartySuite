package com.imposter.app.ui

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.imposter.app.data.GroupRepository
import com.imposter.app.game.Categories
import com.imposter.app.game.Category
import com.imposter.app.game.GameEngine
import com.imposter.app.game.GameType
import com.imposter.app.game.PairCategory
import com.imposter.app.game.Player
import com.imposter.app.game.RoundResult
import com.imposter.app.game.SpyfallLocations
import com.imposter.app.game.Topic
import com.imposter.app.game.UndercoverCategories
import kotlinx.coroutines.launch

/**
 * Shared state for the whole game flow across all game modes: the group, the chosen
 * configuration and the currently dealt round.
 */
class GameViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = GroupRepository(app.applicationContext)

    /** Simple SharedPreferences for lightweight boolean settings. */
    private val prefs = app.getSharedPreferences("imposter_settings", Context.MODE_PRIVATE)

    val names = mutableStateListOf("Spieler 1", "Spieler 2", "Spieler 3", "Spieler 4")

    var gameType by mutableStateOf(GameType.IMPOSTER)
        private set

    /** Ids of activated topics for the current game (Imposter categories / Undercover categories). */
    val selectedCategoryIds = mutableStateListOf<String>()

    var multiSelect by mutableStateOf(false)
        private set
    var allCategories by mutableStateOf(false)
        private set
    var specialCount by mutableStateOf(1)
        private set
    var specialSeesCategory by mutableStateOf(false)
        private set
    var roundResult by mutableStateOf<RoundResult?>(null)
        private set

    /** Whether the reveal order is randomized (persisted). */
    var randomizeOrder by mutableStateOf(prefs.getBoolean(PREF_RANDOMIZE_ORDER, false))
        private set

    /** The player who starts the discussion round (first in the shuffled order, or random pick). */
    var startingPlayer by mutableStateOf<Player?>(null)
        private set

    init {
        viewModelScope.launch {
            repository.loadGroup()?.let { saved ->
                names.clear()
                names.addAll(saved)
            }
        }
    }

    // --- Game selection ---

    fun selectGameType(type: GameType) {
        if (type != gameType) {
            gameType = type
            // Reset topic selection when switching games.
            selectedCategoryIds.clear()
            multiSelect = false
            allCategories = false
            roundResult = null
        }
    }

    // --- Group setup ---

    val playerCount: Int get() = names.size

    fun setPlayerCount(count: Int) {
        val target = count.coerceIn(GameEngine.MIN_PLAYERS, GameEngine.MAX_PLAYERS)
        while (names.size < target) names.add("Spieler ${names.size + 1}")
        while (names.size > target) names.removeAt(names.lastIndex)
    }

    fun updateName(index: Int, value: String) {
        if (index in names.indices) names[index] = value
    }

    fun players(): List<Player> =
        names.mapIndexed { index, name ->
            Player(id = index + 1, name = name.ifBlank { "Spieler ${index + 1}" })
        }

    fun persistGroup() {
        val snapshot = names.toList()
        viewModelScope.launch { repository.saveGroup(snapshot) }
    }

    // --- Topic selection (Imposter & Undercover) ---

    /** Whether the current game shows a category grid. Spyfall uses a fixed location pool. */
    fun usesCategorySelection(): Boolean = gameType != GameType.SPYFALL

    /** Whether the current game offers the "special sees category" option (Imposter only). */
    fun usesSeesCategory(): Boolean = gameType == GameType.IMPOSTER

    fun currentTopics(): List<Topic> = when (gameType) {
        GameType.IMPOSTER -> Categories.ALL
        GameType.UNDERCOVER -> UndercoverCategories.ALL
        GameType.SPYFALL -> emptyList()
    }

    fun effectiveTopics(): List<Topic> =
        if (allCategories) currentTopics()
        else selectedCategoryIds.mapNotNull { id -> currentTopics().firstOrNull { it.id == id } }

    val activeCategoryCount: Int get() = if (allCategories) currentTopics().size else selectedCategoryIds.size

    fun isCategoryActive(id: String): Boolean = allCategories || selectedCategoryIds.contains(id)

    fun onCategoryTap(topic: Topic) {
        if (allCategories) return
        if (multiSelect) {
            if (selectedCategoryIds.contains(topic.id)) selectedCategoryIds.remove(topic.id)
            else selectedCategoryIds.add(topic.id)
        } else {
            selectedCategoryIds.clear()
            selectedCategoryIds.add(topic.id)
        }
    }

    fun updateMultiSelect(value: Boolean) {
        multiSelect = value
        if (!value) {
            val first = selectedCategoryIds.firstOrNull()
            selectedCategoryIds.clear()
            if (first != null) selectedCategoryIds.add(first)
        }
    }

    fun updateAllCategories(value: Boolean) {
        allCategories = value
        if (value) multiSelect = true
    }

    // --- Special (imposter/spy/undercover) options ---

    fun specialCountLabel(): String = when (gameType) {
        GameType.IMPOSTER -> "Anzahl Imposter"
        GameType.SPYFALL -> "Anzahl Spione"
        GameType.UNDERCOVER -> "Anzahl Undercover"
    }

    fun applySuggestedSpecialCount() {
        specialCount = GameEngine.coerceSpecialCount(playerCount, GameEngine.suggestSpecialCount(playerCount))
    }

    fun updateSpecialCount(count: Int) {
        specialCount = GameEngine.coerceSpecialCount(playerCount, count)
    }

    fun updateSpecialSeesCategory(value: Boolean) {
        specialSeesCategory = value
    }

    // --- Randomize order ---

    fun updateRandomizeOrder(value: Boolean) {
        randomizeOrder = value
        prefs.edit().putBoolean(PREF_RANDOMIZE_ORDER, value).apply()
    }

    fun maxSpecials(): Int = GameEngine.maxSpecials(playerCount)

    fun suggestedSpecials(): Int = GameEngine.suggestSpecialCount(playerCount)

    // --- Round ---

    fun canStart(): Boolean =
        if (usesCategorySelection()) effectiveTopics().isNotEmpty() else true

    fun deal(): Boolean {
        val count = GameEngine.coerceSpecialCount(playerCount, specialCount)
        val result: RoundResult? = when (gameType) {
            GameType.IMPOSTER -> {
                val cats = effectiveTopics().filterIsInstance<Category>()
                if (cats.isEmpty()) return false
                GameEngine.dealImposter(cats, players(), count)
            }
            GameType.UNDERCOVER -> {
                val cats = effectiveTopics().filterIsInstance<PairCategory>()
                if (cats.isEmpty()) return false
                GameEngine.dealUndercover(cats, players(), count)
            }
            GameType.SPYFALL -> {
                GameEngine.dealSpyfall(SpyfallLocations.LOCATIONS, players(), count)
            }
        }
        if (result == null) return false

        // Optionally shuffle assignment order for the reveal phase.
        val finalResult = if (randomizeOrder) {
            result.copy(assignments = result.assignments.shuffled())
        } else {
            result
        }
        roundResult = finalResult
        // The starting player is the first in the (possibly shuffled) reveal order.
        startingPlayer = finalResult.assignments.first().player
        return true
    }

    fun dealAgain(): Boolean = deal()

    fun clearRound() {
        roundResult = null
        startingPlayer = null
    }

    companion object {
        private const val PREF_RANDOMIZE_ORDER = "randomize_order"
    }
}
