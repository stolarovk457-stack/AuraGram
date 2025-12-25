package com.aura.auragram.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class StickerRepository(private val dao: StickerDao, private val context: Context) {
    suspend fun insertSticker(sticker: StickerEntity) = dao.insert(sticker)
    fun getStickersFlow(): Flow<List<StickerEntity>> = dao.getAll()
    suspend fun deleteById(id: Long) = dao.deleteById(id)

    // Удобный wrapper для сохранения локального файла (filename — относительный путь в filesDir)
    suspend fun insertStickerLocal(filename: String) {
        dao.insert(StickerEntity(filename = filename))
    }
}