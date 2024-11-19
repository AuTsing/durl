package com.autsing.durl.ui.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.autsing.durl.model.Response

@Composable
fun ResponseContent(
    responses: List<Response> = emptyList(),
) {
    LazyColumn {
        items(responses) {
            ListItem(
                overlineContent = {
                    Text(it.requestAt)
                },
                headlineContent = {
                    Text("请求: ${it.request.name}")
                },
                supportingContent = {
                    if (it.body.isEmpty()) {
                        Text("响应: Pending...")
                    } else {
                        Text("响应: ${it.body}")
                    }
                },
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}
