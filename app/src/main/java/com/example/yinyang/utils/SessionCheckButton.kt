package com.example.yinyang.utils

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.yinyang.network.client
import io.github.jan.supabase.gotrue.gotrue

@Composable
fun CheckSessionButton() {
    Button(onClick = {
        println(client.gotrue.sessionStatus.value)
    }) {
        Text(text = "Check Session status")
    }
}