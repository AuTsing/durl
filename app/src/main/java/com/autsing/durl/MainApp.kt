package com.autsing.durl

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.autsing.durl.repository.RequestsRepository
import com.autsing.durl.repository.ResponsesRepository

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "pref_durl")

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()

        RequestsRepository(this).let { RequestsRepository.instance = it }
        ResponsesRepository(this).let { ResponsesRepository.instance = it }
    }
}
