package com.autsing.durl.navigation

import com.autsing.durl.R

sealed class MainDestination(
    override val route: String,
    override val label: String,
    override val iconId: Int = R.drawable.baseline_android_24,
) : Destination {
    companion object {
        val list = listOf(
            Request,
            Response,
        )
    }

    data object Request : MainDestination(
        route = "/request",
        label = "请求",
        iconId = R.drawable.baseline_send_24,
    )

    data object Response : MainDestination(
        route = "/response",
        label = "响应",
        iconId = R.drawable.baseline_notes_24,
    )
}
