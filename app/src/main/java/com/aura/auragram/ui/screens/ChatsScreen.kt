package com.aura.auragram.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChatsScreen(
    onOpenChat: (Long) -> Unit,
    onSaved: () -> Unit,
    onSettings: () -> Unit,
    onSupport: () -> Unit,
    onStickers: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { onSaved() }) { Text("Сохранённые удалённые") }
            Button(onClick = { onSettings() }) { Text("Настройки") }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { onSupport() }) { Text("Поддержка") }
            Button(onClick = { onStickers() }) { Text("Создать стикер") }
        }
        Spacer(Modifier.height(12.dp))
        Card(modifier = Modifier.fillMaxWidth().clickable { onOpenChat(1L) }) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Чат: Тестовый диалог")
                Text("Последнее сообщение...")
            }
        }
    }
}