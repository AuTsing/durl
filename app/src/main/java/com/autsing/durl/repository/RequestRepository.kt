package com.autsing.durl.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.autsing.durl.model.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class RequestRepository(
    private val dataStore: DataStore<Preferences>,
) {
    companion object {
        private const val PREF_KEY_REQUESTS = "pref_key_requests"
        private val prefKeyRequests = stringPreferencesKey(PREF_KEY_REQUESTS)
    }

    private val requests: MutableStateFlow<List<Request>> = MutableStateFlow(emptyList())

    suspend fun loadRequests() = withContext(Dispatchers.IO) {
        dataStore.data
            .map { it[prefKeyRequests] ?: "" }
            .first()
            .let { runCatching { Json.decodeFromString<List<Request>>(it) } }
            .getOrDefault(emptyList())
            .let { requests.value = it }
    }

    fun getRequests(): List<Request> {
        return requests.value
    }

    fun observeRequests(): Flow<List<Request>> {
        return requests
    }

    fun addRequest(request: Request) {
        requests.value
            .toMutableList()
            .let { requests.value = it }
    }
}
