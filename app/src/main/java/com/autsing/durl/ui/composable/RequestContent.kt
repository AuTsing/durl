package com.autsing.durl.ui.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.autsing.durl.model.Request

@Composable
fun RequestContent(
    innerPadding: PaddingValues,
    requests: List<Request>,
) {
    Surface(modifier = Modifier.padding(innerPadding)) {
        LazyColumn {
            items(requests) {
                ListItem(
                    leadingContent = {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = "Request icon",
                        )
                    },
                    headlineContent = { Text(it.url) },
                )
            }
        }
    }
}
