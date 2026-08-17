package com.imposter.app.game

import kotlin.random.Random

/**
 * Pure-Kotlin game logic — no Android dependencies, fully unit-testable.
 *
 * Handles all three modes. The "special" player(s) are the imposter / spy / undercover;
 * they always stay a strict minority of the group.
 */
object GameEngine {

    const val MIN_PLAYERS = 3
    const val MAX_PLAYERS = 15

    /**
     * Suggested number of special players based on group size:
     *  - 3–5 players  -> 1
     *  - 6–10 players -> 2
     *  - 11–15 players -> 3
     */
    fun suggestSpecialCount(players: Int): Int = when {
        players <= 5 -> 1
        players <= 10 -> 2
        else -> 3
    }

    /**
     * Maximum number of special players so the rest keep a strict majority.
     * = floor((players - 1) / 2), never below 1.
     */
    fun maxSpecials(players: Int): Int = maxOf(1, (players - 1) / 2)

    /** Clamp an arbitrary desired special count into the valid [1, maxSpecials] range. */
    fun coerceSpecialCount(players: Int, desired: Int): Int =
        desired.coerceIn(1, maxSpecials(players))

    private fun validate(players: List<Player>, specialCount: Int) {
        require(players.size in MIN_PLAYERS..MAX_PLAYERS) {
            "Player count must be between $MIN_PLAYERS and $MAX_PLAYERS, was ${players.size}"
        }
        require(specialCount in 1..maxSpecials(players.size)) {
            "Special count $specialCount out of range for ${players.size} players " +
                "(allowed 1..${maxSpecials(players.size)})"
        }
    }

    private fun pickSpecialIds(players: List<Player>, count: Int, random: Random): Set<Int> =
        players.map { it.id }.shuffled(random).take(count).toSet()

    // --- Imposter: crew shares one word, imposters get nothing ---

    fun dealImposter(
        categories: List<Category>,
        players: List<Player>,
        imposterCount: Int,
        random: Random = Random.Default,
    ): RoundResult {
        validate(players, imposterCount)
        val usable = categories.filter { it.words.isNotEmpty() }
        require(usable.isNotEmpty()) { "No categories with words selected" }

        val category = usable.random(random)
        val word = category.words.random(random)
        val specialIds = pickSpecialIds(players, imposterCount, random)

        val assignments = players.map { p ->
            val special = p.id in specialIds
            RoleAssignment(player = p, word = if (special) null else word, isSpecial = special)
        }
        return RoundResult(
            gameType = GameType.IMPOSTER,
            topicName = category.name,
            topicEmoji = category.emoji,
            secretWord = word,
            altWord = null,
            assignments = assignments,
        )
    }

    // --- Spyfall: everyone knows the location, the spy does not ---

    fun dealSpyfall(
        locations: List<String>,
        players: List<Player>,
        spyCount: Int,
        random: Random = Random.Default,
    ): RoundResult {
        validate(players, spyCount)
        require(locations.isNotEmpty()) { "No locations available" }

        val location = locations.random(random)
        val specialIds = pickSpecialIds(players, spyCount, random)

        val assignments = players.map { p ->
            val special = p.id in specialIds
            RoleAssignment(player = p, word = if (special) null else location, isSpecial = special)
        }
        return RoundResult(
            gameType = GameType.SPYFALL,
            topicName = "Ort",
            topicEmoji = "🌐",
            secretWord = location,
            altWord = null,
            assignments = assignments,
        )
    }

    // --- Undercover: majority gets word A, undercover(s) get related word B ---

    fun dealUndercover(
        categories: List<PairCategory>,
        players: List<Player>,
        undercoverCount: Int,
        random: Random = Random.Default,
    ): RoundResult {
        validate(players, undercoverCount)
        val usable = categories.filter { it.pairs.isNotEmpty() }
        require(usable.isNotEmpty()) { "No pair categories selected" }

        val category = usable.random(random)
        val pair = category.pairs.random(random)
        val specialIds = pickSpecialIds(players, undercoverCount, random)

        val assignments = players.map { p ->
            val special = p.id in specialIds
            RoleAssignment(
                player = p,
                word = if (special) pair.undercover else pair.civilian,
                isSpecial = special,
            )
        }
        return RoundResult(
            gameType = GameType.UNDERCOVER,
            topicName = category.name,
            topicEmoji = category.emoji,
            secretWord = pair.civilian,
            altWord = pair.undercover,
            assignments = assignments,
        )
    }
}
