package com.project.h.s.objectrecognitionsdk.data.entities

import android.graphics.RectF

data class MobileNetV1ModelResult(
    val location: RectF?,
    val category: String?,
    val score: Float?
)
