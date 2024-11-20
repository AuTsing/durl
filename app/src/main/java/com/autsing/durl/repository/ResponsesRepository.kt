package com.autsing.durl.repository

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.autsing.durl.dataStore
import com.autsing.durl.model.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ResponsesRepository(
    private val context: Context,
) {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: ResponsesRepository

        private const val PREF_KEY_RESPONSES = "pref_key_responses"
        private val prefKeyResponses = stringPreferencesKey(PREF_KEY_RESPONSES)
        private const val PREF_KEY_UNREAD_COUNT = "pref_key_unread_count"
        private val prefKeyUnreadCount = intPreferencesKey(PREF_KEY_UNREAD_COUNT)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore
    private val responses: MutableStateFlow<List<Response>> = MutableStateFlow(emptyList())
    private val unreadCount: MutableStateFlow<Int> = MutableStateFlow(0)

    suspend fun loadResponses() = withContext(Dispatchers.IO) {
        dataStore.data
            .map { it[prefKeyResponses] ?: "" }
            .first()
            .let { runCatching { Json.decodeFromString<List<Response>>(it) } }
            .getOrDefault(emptyList())
            .let { responses.value = it }
    }

    suspend fun loadUnreadCount() = withContext(Dispatchers.IO) {
        dataStore.data
            .map { it[prefKeyUnreadCount] ?: 0 }
            .first()
            .let { unreadCount.value = it }
    }

    fun getResponses(): List<Response> {
        return responses.value
    }

    fun getUnreadCount(): Int {
        return unreadCount.value
    }

    fun observeResponses(): Flow<List<Response>> {
        return responses
    }

    fun observeUnreadCount(): Flow<Int> {
        return unreadCount
    }

    suspend fun addResponse(response: Response) = withContext(Dispatchers.IO) {
        responses.value
            .toMutableList()
            .apply { add(response) }
            .let { responses.value = it }
        unreadCount.value += 1
        dataStore.edit { it[prefKeyResponses] = Json.encodeToString(responses.value) }
        dataStore.edit { it[prefKeyUnreadCount] = unreadCount.value }
        return@withContext
    }

    suspend fun updateResponse(
        response: Response,
        newResponse: Response,
    ) = withContext(Dispatchers.IO) {
        synchronized(responses.value) {
            responses.value
                .toMutableList()
                .apply {
                    val index = indexOf(response)
                    if (index == -1) return@apply
                    this[index] = newResponse
                }
                .let { responses.value = it }
        }
        dataStore.edit { it[prefKeyResponses] = Json.encodeToString(responses.value) }
        return@withContext
    }

    suspend fun clearResponses() = withContext(Dispatchers.IO) {
        responses.value = emptyList()
        dataStore.edit { it[prefKeyResponses] = Json.encodeToString(responses.value) }
        return@withContext
    }

    suspend fun resetUnreadCount() = withContext(Dispatchers.IO) {
        unreadCount.value = 0
        dataStore.edit { it[prefKeyUnreadCount] = 0 }
        return@withContext
    }
}
