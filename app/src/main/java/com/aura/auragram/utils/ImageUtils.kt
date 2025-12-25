package com.aura.auragram.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import java.io.File
import java.io.FileOutputStream

object ImageUtils {
    // Центрированный crop и resize в квадрат размера targetPx
    fun centerCropAndResize(source: Bitmap, targetPx: Int): Bitmap {
        val srcW = source.width
        val srcH = source.height
        val size = minOf(srcW, srcH)
        val left = (srcW - size) / 2
        val top = (srcH - size) / 2
        val cropped = Bitmap.createBitmap(source, left, top, size, size)
        val scaled = Bitmap.createScaledBitmap(cropped, targetPx, targetPx, true)
        if (cropped != source) cropped.recycle()
        return scaled
    }

    // Сохранить bitmap как WebP в filesDir/stickers/filename
    fun saveBitmapWebP(context: Context, bitmap: Bitmap, filename: String): Boolean {
        return try {
            val dir = File(context.filesDir, "stickers")
            if (!dir.exists()) dir.mkdirs()
            val file = File(dir, filename)
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.WEBP_LOSSLESS, 100, fos)
            fos.flush()
            fos.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}