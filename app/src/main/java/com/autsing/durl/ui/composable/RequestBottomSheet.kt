package com.autsing.durl.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.autsing.durl.R
import com.autsing.durl.model.Request

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestBottomSheet(
    maybeRequest: Request? = null,
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismissRequest: () -> Unit = {},
    onClickSendRequest: (Request) -> Unit = {},
    onClickRemoveRequest: (Request) -> Unit = {},
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
    ) {
        if (maybeRequest == null) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        } else {
            Column {
                ListItem(
                    headlineContent = { Text(maybeRequest.name) },
                )
                ListItem(
                    leadingContent = {
                        Icon(
                            painter = painterResource(R.drawable.baseline_send_24),
                            contentDescription = "Send request",
                        )
                    },
                    headlineContent = { Text("发送") },
                    modifier = Modifier.clickable { onClickSendRequest(maybeRequest) },
                )
                ListItem(
                    leadingContent = {
                        Icon(
                            painter = painterResource(R.drawable.baseline_delete_24),
                            contentDescription = "Delete request",
                        )
                    },
                    headlineContent = { Text("删除") },
                    modifier = Modifier.clickable { onClickRemoveRequest(maybeRequest) }
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun RequestBottomSheetPreview() {
    RequestBottomSheet(
        maybeRequest = Request(),
        sheetState = rememberStandardBottomSheetState(SheetValue.Expanded),
    )
}
