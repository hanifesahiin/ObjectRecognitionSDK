package com.project.h.s.objectrecognitionsdk.domain.entities

import android.graphics.Bitmap

data class MobileNetModelResponse(
    val processedBitmap: Bitmap?,
    val category: String
)
