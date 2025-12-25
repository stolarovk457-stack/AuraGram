package com.aura.auragram.ui.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aura.auragram.data.StickerRepository
import com.aura.auragram.utils.ImageUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

@Composable
fun StickerCreatorScreen(stickers: StickerRepository) {
    val ctx = LocalContext.current
    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var status by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedUri = uri
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Создать локальный стикерпак", modifier = Modifier.padding(bottom = 12.dp))
        OutlinedButton(onClick = { launcher.launch("image/*") }) {
            Text("Выбрать изображение")
        }

        Spacer(modifier = Modifier.height(12.dp))

        selectedUri?.let { uri ->
            AsyncImage(model = uri, contentDescription = "Выбранное изображение", modifier = Modifier
                .fillMaxWidth()
                .height(300.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                // Сохраняем в фоновом потоке
                status = "Сохраняю..."
                val u = uri
                LaunchedEffect(u) {
                    val result = withContext(Dispatchers.IO) {
                        try {
                            val input: InputStream? = ctx.contentResolver.openInputStream(u)
                            val bmp = BitmapFactory.decodeStream(input)
                            input?.close()
                            // Приводим к square 512x512
                            val stickerBmp = ImageUtils.centerCropAndResize(bmp, 512)
                            val filename = "sticker_${System.currentTimeMillis()}.webp"
                            val saved = ImageUtils.saveBitmapWebP(ctx, stickerBmp, filename)
                            if (saved) {
                                stickers.insertStickerLocal(filename)
                            }
                            saved
                        } catch (e: Exception) {
                            e.printStackTrace()
                            false
                        }
                    }
                    status = if (result) "Стикер сохранён" else "Ошибка при сохранении"
                }
            }) {
                Text("Сохранить как стикер")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        status?.let { Text(it) }
    }
}