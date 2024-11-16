package com.autsing.durl.ui.composable

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.autsing.durl.ui.theme.DurlTheme
import com.autsing.durl.viewmodel.RequestViewModel

@Composable
fun MainGraph(
    requestViewModel: RequestViewModel = viewModel(),
) {
    DurlTheme {
        MainScreen()
    }
}
