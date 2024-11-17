package com.autsing.durl.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.autsing.durl.R
import com.autsing.durl.model.Request

@Composable
fun RequestContent(
    requests: List<Request>,
    onClickRequest: (Request) -> Unit = {},
    onClickSendRequest: (Request) -> Unit = {},
) {
    LazyColumn {
        items(requests) {
            ListItem(
                leadingContent = {
                    OutlinedIconButton(onClick = { }) {
                        Text(
                            text = it.name.first().toString(),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                },
                headlineContent = {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                },
                supportingContent = {
                    Text(
                        text = it.url,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                trailingContent = {
                    IconButton(onClick = { onClickSendRequest(it) }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_send_24),
                            contentDescription = "Send request",
                        )
                    }
                },
                modifier = Modifier.clickable { onClickRequest(it) },
            )
        }
    }
}
