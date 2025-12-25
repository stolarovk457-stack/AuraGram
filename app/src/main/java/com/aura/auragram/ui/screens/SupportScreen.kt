package com.aura.auragram.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun SupportScreen() {
    // Простая реализация: открываем ссылку на бота. Замените BOT_USERNAME_PLACEHOLDER на вашего бота.
    val ctx = LocalContext.current
    var message by remember { mutableStateOf("") }
    var showConfirmation by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Поддержка", modifier = Modifier.padding(bottom = 8.dp))
        OutlinedTextField(value = message, onValueChange = { message = it }, modifier = Modifier.fillMaxWidth(), label = { Text("Сообщение для поддержки") })
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            // Открываем телеграм-бота через deep link. Пользователь должен убедиться, что написал боту.
            val botUser = "BOT_USERNAME_PLACEHOLDER" // <- замените на реальное имя бота без @
            val url = "https://t.me/$botUser"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            ctx.startActivity(intent)
            // Показываем локальное подтверждение пользователю
            showConfirmation = true
        }) {
            Text("Написать в поддержку (откроется Telegram)")
        }
    }

    if (showConfirmation) {
        AlertDialog(
            onDismissRequest = { showConfirmation = false },
            title = { Text("Заявка отправлена") },
            text = { Text("После того как вы напишете боту, в панель админа придёт сообщение. Поддержка свяжется с вами.") },
            confirmButton = {
                TextButton(onClick = { showConfirmation = false }) { Text("Ок") }
            }
        )
    }
}