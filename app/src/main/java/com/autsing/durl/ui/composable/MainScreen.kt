package com.autsing.durl.ui.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.autsing.durl.R
import com.autsing.durl.model.Request
import com.autsing.durl.ui.theme.DurlTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    requests: List<Request> = emptyList(),
    onClickAddRequest: () -> Unit = {},
    onClickNavigateToRequest: () -> Unit = {},
    onClickNavigateToResponse: () -> Unit = {},
) {
    DurlTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("请求") },
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
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.baseline_send_24),
                                contentDescription = "Request navigation",
                            )
                        },
                        label = { Text("请求") },
                        onClick = onClickNavigateToRequest,
                        selected = false,
                    )
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.baseline_notes_24),
                                contentDescription = "Response navigation",
                            )
                        },
                        label = { Text("响应") },
                        onClick = onClickNavigateToResponse,
                        selected = false,
                    )
                }
            },
        ) { innerPadding ->
            Surface(modifier = Modifier.padding(innerPadding)) {
                RequestContent(
                    requests = requests,
                )
            }
        }
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
