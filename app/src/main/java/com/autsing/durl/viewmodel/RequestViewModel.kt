package com.autsing.durl.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autsing.durl.activity.AddRequestActivity
import com.autsing.durl.model.Request
import com.autsing.durl.model.Response
import com.autsing.durl.repository.RequestsRepository
import com.autsing.durl.repository.ResponsesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.coroutines.executeAsync

class RequestViewModel : ViewModel() {
    private val requestsRepository: RequestsRepository = RequestsRepository.instance
    private val responsesRepository: ResponsesRepository = ResponsesRepository.instance
    private val httpClient: OkHttpClient = OkHttpClient()

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

    @OptIn(ExperimentalCoroutinesApi::class)
    fun handleClickSendRequest(request: Request) = viewModelScope.launch {
        val response = Response(request = request)

        responsesRepository.addResponse(response)

        val body = runCatching {
            withContext(Dispatchers.IO) {
                val req = okhttp3.Request.Builder()
                    .url(request.url)
                    .build()
                val res = httpClient.newCall(req).executeAsync()

                val contentType = res.headers["content-type"] ?: "unknown"
                if (!contentType.contains("application/json")) {
                    return@withContext "不支持的响应类型 $contentType"
                }

                res.body.string()
            }
        }.getOrElse {
            it.message ?: ""
        }

        val newResponse = response.copy(
            responseAt = Response.getTimestamp(),
            body = body,
        )
        responsesRepository.updateResponse(response, newResponse)
    }

    fun handleClickRemoveRequest(request: Request) = viewModelScope.launch {
        requestsRepository.removeRequest(request)
    }
}
