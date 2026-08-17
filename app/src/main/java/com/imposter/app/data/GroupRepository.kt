package com.imposter.app.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class PersistedGroup(val names: List<String>)

private val Context.groupDataStore by preferencesDataStore(name = "imposter_group")

/**
 * Persists the most recently used group (player names) so it is pre-filled next launch.
 * All disk access happens on the DataStore's own IO dispatcher.
 */
class GroupRepository(private val context: Context) {

    private val key = stringPreferencesKey("last_group_json")
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun loadGroup(): List<String>? {
        val stored = context.groupDataStore.data
            .map { it[key] }
            .first() ?: return null
        return runCatching { json.decodeFromString<PersistedGroup>(stored).names }
            .getOrNull()
            ?.takeIf { it.isNotEmpty() }
    }

    suspend fun saveGroup(names: List<String>) {
        val payload = json.encodeToString(PersistedGroup(names))
        context.groupDataStore.edit { it[key] = payload }
    }
}
