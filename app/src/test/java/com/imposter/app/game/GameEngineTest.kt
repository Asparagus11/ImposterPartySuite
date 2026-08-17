package com.imposter.app.game

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random

class GameEngineTest {

    private fun players(n: Int): List<Player> =
        (1..n).map { Player(id = it, name = "Spieler $it") }

    private val tiere = Category("tiere", "Tiere", "🐾", listOf("Hund", "Katze", "Löwe", "Tiger"))
    private val essen = Category("essen", "Essen", "🍔", listOf("Pizza", "Burger", "Sushi"))

    private val locations = listOf("Flughafen", "Casino", "U-Boot", "Zirkus")

    private val pairCats = listOf(
        PairCategory(
            "tiere", "Tiere", "🐾",
            listOf(WordPair("Katze", "Tiger"), WordPair("Hund", "Wolf")),
        ),
    )

    // --- special-count helpers ---

    @Test
    fun suggestSpecialCount_matchesBands() {
        assertEquals(1, GameEngine.suggestSpecialCount(3))
        assertEquals(1, GameEngine.suggestSpecialCount(5))
        assertEquals(2, GameEngine.suggestSpecialCount(6))
        assertEquals(2, GameEngine.suggestSpecialCount(10))
        assertEquals(3, GameEngine.suggestSpecialCount(11))
        assertEquals(3, GameEngine.suggestSpecialCount(15))
    }

    @Test
    fun maxSpecials_keepsMajority() {
        assertEquals(1, GameEngine.maxSpecials(3))
        assertEquals(1, GameEngine.maxSpecials(4))
        assertEquals(2, GameEngine.maxSpecials(5))
        assertEquals(7, GameEngine.maxSpecials(15))
    }

    @Test
    fun maxSpecials_alwaysAtLeastOneAndMinority() {
        for (n in GameEngine.MIN_PLAYERS..GameEngine.MAX_PLAYERS) {
            val m = GameEngine.maxSpecials(n)
            assertTrue("n=$n", m >= 1)
            assertTrue("n=$n", m < n - m)
        }
    }

    @Test
    fun coerceSpecialCount_clampsIntoRange() {
        assertEquals(1, GameEngine.coerceSpecialCount(4, 0))
        assertEquals(1, GameEngine.coerceSpecialCount(4, 5))
        assertEquals(7, GameEngine.coerceSpecialCount(15, 99))
        assertEquals(2, GameEngine.coerceSpecialCount(10, 2))
    }

    // --- Imposter ---

    @Test
    fun imposter_crewSharesWord_impostersHaveNone() {
        val r = GameEngine.dealImposter(listOf(tiere), players(6), imposterCount = 2, random = Random(7))
        assertEquals(GameType.IMPOSTER, r.gameType)
        assertEquals(2, r.specials.size)
        val crew = r.assignments.filter { !it.isSpecial }
        assertEquals(4, crew.size)
        crew.forEach { assertEquals(r.secretWord, it.word) }
        r.assignments.filter { it.isSpecial }.forEach { assertNull(it.word) }
        assertTrue(r.secretWord in tiere.words)
        assertNull(r.altWord)
    }

    @Test
    fun imposter_multipleCategories_wordMatchesTopic() {
        repeat(20) { seed ->
            val r = GameEngine.dealImposter(listOf(tiere, essen), players(5), 1, Random(seed.toLong()))
            val cat = if (r.topicName == "Tiere") tiere else essen
            assertTrue(r.secretWord in cat.words)
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun imposter_rejectsTooFewPlayers() {
        GameEngine.dealImposter(listOf(tiere), players(2), 1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun imposter_rejectsTooManySpecials() {
        GameEngine.dealImposter(listOf(tiere), players(4), 2)
    }

    // --- Spyfall ---

    @Test
    fun spyfall_crewGetsLocation_spyGetsNone() {
        val r = GameEngine.dealSpyfall(locations, players(7), spyCount = 1, random = Random(3))
        assertEquals(GameType.SPYFALL, r.gameType)
        assertEquals("Ort", r.topicName)
        assertEquals(1, r.specials.size)
        assertTrue(r.secretWord in locations)
        r.assignments.forEach {
            if (it.isSpecial) assertNull(it.word) else assertEquals(r.secretWord, it.word)
        }
    }

    @Test
    fun spyfall_boundaryPlayers() {
        val min = GameEngine.dealSpyfall(locations, players(3), 1, Random(1))
        assertEquals(1, min.specials.size)
        val max = GameEngine.dealSpyfall(locations, players(15), 7, Random(1))
        assertEquals(7, max.specials.size)
        assertTrue(max.specials.size < 15 - max.specials.size)
    }

    // --- Undercover ---

    @Test
    fun undercover_majorityAndUndercoverGetDifferentWords() {
        val r = GameEngine.dealUndercover(pairCats, players(6), undercoverCount = 2, random = Random(11))
        assertEquals(GameType.UNDERCOVER, r.gameType)
        assertEquals(2, r.specials.size)
        assertTrue(r.altWord != null)
        assertTrue(r.secretWord != r.altWord)
        r.assignments.forEach {
            if (it.isSpecial) assertEquals(r.altWord, it.word) else assertEquals(r.secretWord, it.word)
        }
        // words come from the same pair
        val pair = pairCats[0].pairs.first { it.civilian == r.secretWord && it.undercover == r.altWord }
        assertEquals(pair.civilian, r.secretWord)
    }

    @Test(expected = IllegalArgumentException::class)
    fun undercover_rejectsTooManySpecials() {
        GameEngine.dealUndercover(pairCats, players(4), 2)
    }
}

class CategoriesTest {

    @Test
    fun allCategoriesPresent() {
        assertEquals(16, Categories.ALL.size)
    }

    @Test
    fun everyCategoryHasEnoughUniqueWords() {
        Categories.ALL.forEach { cat ->
            assertTrue("${cat.name} needs >=25 words, had ${cat.words.size}", cat.words.size >= 25)
            assertEquals("${cat.name} has duplicate words", cat.words.size, cat.words.toSet().size)
        }
    }

    @Test
    fun categoryIdsAreUnique() {
        val ids = Categories.ALL.map { it.id }
        assertEquals(ids.size, ids.toSet().size)
    }
}
