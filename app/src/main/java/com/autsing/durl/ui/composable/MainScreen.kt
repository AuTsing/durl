package com.autsing.durl.ui.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.autsing.durl.model.Request

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    requests: List<Request> = emptyList(),
    onClickAddRequest: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("请求")
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onClickAddRequest) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add request",
                )
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        RequestContent(
            innerPadding = innerPadding,
            requests = requests,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreen(
        requests = listOf(
            Request(),
            Request(),
            Request(),
            Request(),
            Request(),
        ),
    )
}
