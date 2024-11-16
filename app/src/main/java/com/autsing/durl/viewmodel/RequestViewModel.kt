package com.autsing.durl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autsing.durl.repository.RequestsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class RequestViewModel : ViewModel() {
    private val requestsRepository: RequestsRepository = RequestsRepository.instance

    val requests = requestsRepository.observeRequests()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList(),
        )
}
