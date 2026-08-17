package com.imposter.app.data

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PersistedGroupTest {

    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun roundtrip_preservesNamesAndOrder() {
        val original = PersistedGroup(listOf("Anna", "Ben", "Chris", "Dilek", "Emre"))
        val encoded = json.encodeToString(original)
        val decoded = json.decodeFromString<PersistedGroup>(encoded)
        assertEquals(original, decoded)
        assertEquals(listOf("Anna", "Ben", "Chris", "Dilek", "Emre"), decoded.names)
    }

    @Test
    fun roundtrip_handlesSpecialCharacters() {
        val original = PersistedGroup(listOf("Jörg", "María", "李雷", "A \"B\""))
        val decoded = json.decodeFromString<PersistedGroup>(json.encodeToString(original))
        assertEquals(original, decoded)
    }
}
