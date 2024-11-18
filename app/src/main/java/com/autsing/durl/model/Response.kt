package com.autsing.durl.model

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
data class Response(
    val request: Request,
    val requestAt: String = getTimestamp(),
    var responseAt: String = "",
    var body: String = "",
) {
    companion object {
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        fun getTimestamp(): String {
            return LocalDateTime.now().format(formatter)
        }
    }
}
