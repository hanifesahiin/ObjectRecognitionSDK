package com.project.h.s.objectrecognitionsdk.domain.repository

import android.graphics.Bitmap
import com.project.h.s.objectrecognitionsdk.data.entities.MobileNetV1ModelResult

interface MobileNetV1Model {
    suspend fun processBitmap(selectedImage: Bitmap): Bitmap?
    fun results(): MobileNetV1ModelResult
    fun closeModel()
}