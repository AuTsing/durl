package com.autsing.durl.ui.composable

import android.content.Context
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.autsing.durl.activity.AddRequestActivity
import com.autsing.durl.model.Request
import com.autsing.durl.repository.RequestsRepository
import com.autsing.durl.ui.theme.DurlTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRequestScreen() {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var urlValue by remember { mutableStateOf("") }

    DurlTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("添加") },
                )
            },
            bottomBar = {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(NavigationBarDefaults.windowInsets)
                        .padding(16.dp),
                ) {
                    TextButton(onClick = {
                        handleClickCancel(context)
                    }) {
                        Text(
                            text = "取消",
                            modifier = Modifier.padding(4.dp),
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = {
                        coroutineScope.launch {
                            handleClickOk(context, urlValue)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Done add request",
                        )
                        Text(
                            text = "完成",
                            modifier = Modifier.padding(4.dp),
                        )
                    }
                }
            }
        ) { innerPadding ->
            Surface(modifier = Modifier.padding(innerPadding)) {
                AddRequestContent(
                    urlValue = urlValue,
                    onUrlChange = { urlValue = it },
                )
            }
        }
    }
}

@Composable
private fun AddRequestContent(
    urlValue: String = "",
    onUrlChange: (String) -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    LazyColumn(
        contentPadding = PaddingValues(32.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { keyboardController?.hide() })
            },
    ) {
        item {
            TextField(
                value = urlValue,
                onValueChange = onUrlChange,
                label = { Text("URL") },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

private suspend fun handleClickOk(
    context: Context,
    urlValue: String,
) {
    Request(
        url = urlValue,
    ).let { RequestsRepository.instance.addRequest(it) }
    (context as AddRequestActivity).finish()
}

private fun handleClickCancel(context: Context) {
    (context as AddRequestActivity).finish()
}

@Preview(showBackground = true)
@Composable
private fun AddRequestScreenPreview() {
    AddRequestScreen()
}
