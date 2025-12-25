package com.aura.auragram.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stickers")
data class StickerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val filename: String,
    val createdAt: Long = System.currentTimeMillis()
)