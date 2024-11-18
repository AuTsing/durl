package com.autsing.durl.repository

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
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
    }

    private val dataStore: DataStore<Preferences> = context.dataStore
    private val responses: MutableStateFlow<List<Response>> = MutableStateFlow(emptyList())

    suspend fun loadResponses() = withContext(Dispatchers.IO) {
        dataStore.data
            .map { it[prefKeyResponses] ?: "" }
            .first()
            .let { runCatching { Json.decodeFromString<List<Response>>(it) } }
            .getOrDefault(emptyList())
            .let { responses.value = it }
    }

    fun getResponses(): List<Response> {
        return responses.value
    }

    fun observeResponses(): Flow<List<Response>> {
        return responses
    }

    suspend fun addResponse(response: Response) = withContext(Dispatchers.IO) {
        responses.value
            .toMutableList()
            .apply { add(response) }
            .let { responses.value = it }
        dataStore.edit { it[prefKeyResponses] = Json.encodeToString(responses.value) }
        return@withContext
    }

    suspend fun updateResponse(
        response: Response,
        newResponse: Response,
    ) = withContext(Dispatchers.IO) {
        responses.value
            .toMutableList()
            .apply { this[indexOf(response)] = newResponse }
            .let { responses.value = it }
        dataStore.edit { it[prefKeyResponses] = Json.encodeToString(responses.value) }
        return@withContext
    }

    suspend fun clearResponses() = withContext(Dispatchers.IO) {
        responses.value = emptyList()
        dataStore.edit { it[prefKeyResponses] = Json.encodeToString(responses.value) }
        return@withContext
    }
}
