package com.autsing.durl.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autsing.durl.activity.AddRequestActivity
import com.autsing.durl.model.Request
import com.autsing.durl.model.Response
import com.autsing.durl.repository.RequestsRepository
import com.autsing.durl.repository.ResponsesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RequestViewModel : ViewModel() {
    private val requestsRepository: RequestsRepository = RequestsRepository.instance
    private val responsesRepository: ResponsesRepository = ResponsesRepository.instance

    val requests = requestsRepository.observeRequests()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList(),
        )

    init {
        handleLoadRequest()
    }

    private fun handleLoadRequest() = viewModelScope.launch {
        requestsRepository.loadRequests()
    }

    fun handleClickAddRequest(context: Context) {
        AddRequestActivity.startActivity(context)
    }

    fun handleClickSendRequest(request: Request) = viewModelScope.launch {
        responsesRepository.addResponse(
            Response(
                request = request,
            )
        )
        Log.d("TAG", "handleClickSendRequest: ${responsesRepository.getResponses()}")
    }

    fun handleClickRemoveRequest(request: Request) = viewModelScope.launch {
        requestsRepository.removeRequest(request)
    }
}
