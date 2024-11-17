package com.autsing.durl.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.autsing.durl.viewmodel.RequestViewModel

@Composable
fun MainGraph(
    requestViewModel: RequestViewModel = viewModel(),
) {
    val context = LocalContext.current
    val requests by requestViewModel.requests.collectAsState()

    MainScreen(
        requests = requests,
        onClickAddRequest = { requestViewModel.handleClickAddRequest(context) },
        onClickSendRequest = requestViewModel::handleClickSendRequest,
        onClickRemoveRequest = requestViewModel::handleClickRemoveRequest,
    )
}
