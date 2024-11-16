package com.autsing.durl.repository

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.autsing.durl.dataStore
import com.autsing.durl.model.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RequestsRepository(
    private val context: Context,
) {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: RequestsRepository

        private const val PREF_KEY_REQUESTS = "pref_key_requests"
        private val prefKeyRequests = stringPreferencesKey(PREF_KEY_REQUESTS)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore
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

    suspend fun addRequest(request: Request) = withContext(Dispatchers.IO) {
        requests.value
            .toMutableList()
            .apply { add(request) }
            .let { requests.value = it }
        dataStore.edit { it[prefKeyRequests] = Json.encodeToString(requests.value) }
        return@withContext
    }

    suspend fun removeRequest(request: Request) = withContext(Dispatchers.IO) {
        requests.value
            .toMutableList()
            .apply { remove(request) }
            .let { requests.value = it }
        dataStore.edit { it[prefKeyRequests] = Json.encodeToString(requests.value) }
        return@withContext
    }
}
