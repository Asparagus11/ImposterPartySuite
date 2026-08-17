package com.imposter.app.game

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GameDataTest {

    @Test
    fun spyfallLocations_areEnoughAndUnique() {
        val locs = SpyfallLocations.LOCATIONS
        assertTrue("need >=20 locations, had ${locs.size}", locs.size >= 20)
        assertEquals("duplicate locations", locs.size, locs.toSet().size)
        locs.forEach { assertTrue(it.isNotBlank()) }
    }

    @Test
    fun undercoverCategories_haveValidPairs() {
        val cats = UndercoverCategories.ALL
        assertTrue(cats.isNotEmpty())
        assertEquals("duplicate category ids", cats.size, cats.map { it.id }.toSet().size)
        cats.forEach { cat ->
            assertTrue("${cat.name} needs >=8 pairs", cat.pairs.size >= 8)
            cat.pairs.forEach { p ->
                assertTrue("civilian blank in ${cat.name}", p.civilian.isNotBlank())
                assertTrue("undercover blank in ${cat.name}", p.undercover.isNotBlank())
                assertTrue("pair must differ in ${cat.name}", p.civilian != p.undercover)
            }
        }
    }
}
