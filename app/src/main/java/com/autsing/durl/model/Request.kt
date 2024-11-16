package com.autsing.durl.model

import kotlinx.serialization.Serializable

@Serializable
data class Request(
    val url: String = "https://jsonplaceholder.typicode.com/todos/1",
)
