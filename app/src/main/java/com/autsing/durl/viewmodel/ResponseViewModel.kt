package com.autsing.durl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autsing.durl.model.Response
import com.autsing.durl.repository.ResponsesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ResponseViewModel : ViewModel() {
    private val responsesRepository: ResponsesRepository = ResponsesRepository.instance

    val responses: StateFlow<List<Response>> = responsesRepository.observeResponses()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList(),
        )
    val unreadCount: StateFlow<Int> = responsesRepository.observeUnreadCount()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0,
        )

    init {
        handleLoadResponse()
        handleLoadUnreadCount()
    }

    private fun handleLoadResponse() = viewModelScope.launch {
        responsesRepository.loadResponses()
    }

    private fun handleLoadUnreadCount() = viewModelScope.launch {
        responsesRepository.loadUnreadCount()
    }

    fun handleClearResponses() = viewModelScope.launch {
        responsesRepository.clearResponses()
    }

    fun handleResetUnreadCount() = viewModelScope.launch {
        responsesRepository.resetUnreadCount()
    }
}
