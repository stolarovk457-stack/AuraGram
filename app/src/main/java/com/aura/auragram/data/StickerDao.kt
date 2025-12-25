package com.aura.auragram.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StickerDao {
    @Insert
    suspend fun insert(sticker: StickerEntity): Long

    @Query("SELECT * FROM stickers ORDER BY createdAt DESC")
    fun getAll(): Flow<List<StickerEntity>>

    @Query("DELETE FROM stickers WHERE id = :id")
    suspend fun deleteById(id: Long)
}