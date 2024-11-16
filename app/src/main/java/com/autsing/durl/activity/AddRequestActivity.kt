package com.autsing.durl.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.autsing.durl.ui.composable.AddRequestScreen

class AddRequestActivity : ComponentActivity() {
    companion object {
        fun startActivity(context: Context) {
            Intent(context, AddRequestActivity::class.java)
                .let { context.startActivity(it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { AddRequestScreen() }
    }
}
