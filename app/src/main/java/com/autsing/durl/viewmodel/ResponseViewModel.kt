package com.autsing.durl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autsing.durl.repository.ResponsesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ResponseViewModel : ViewModel() {
    private val responsesRepository: ResponsesRepository = ResponsesRepository.instance

    val responses = responsesRepository.observeResponses()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList(),
        )

    init {
        handleLoadResponse()
    }

    private fun handleLoadResponse() = viewModelScope.launch {
        responsesRepository.loadResponses()
    }

    fun handleClearResponses() = viewModelScope.launch {
        responsesRepository.clearResponses()
    }
}
