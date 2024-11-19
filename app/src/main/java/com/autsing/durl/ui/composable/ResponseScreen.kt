package com.autsing.durl.ui.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.autsing.durl.model.Request
import com.autsing.durl.model.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResponseScreen(
    responses: List<Response> = emptyList(),
    onClickClearResponses: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("响应") },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onClickClearResponses) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear responses",
                )
            }
        },
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            ResponseContent(
                responses = responses,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RequestScreenPreview() {
    val request = Request()
    ResponseScreen(
        responses = listOf(
            Response(request),
            Response(request),
            Response(request),
            Response(request),
            Response(request),
        ),
    )
}
