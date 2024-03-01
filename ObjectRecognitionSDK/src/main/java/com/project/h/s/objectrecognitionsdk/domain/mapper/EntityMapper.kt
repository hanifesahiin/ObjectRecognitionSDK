package com.project.h.s.objectrecognitionsdk.domain.mapper

import android.graphics.Bitmap
import com.project.h.s.objectrecognitionsdk.data.entities.MobileNetV1ModelResult
import com.project.h.s.objectrecognitionsdk.data.entities.UserItemResponse
import com.project.h.s.objectrecognitionsdk.domain.entities.MobileNetModelResponse
import com.project.h.s.objectrecognitionsdk.domain.entities.UserItem

fun UserItemResponse.toUserItem() = UserItem(
    id = this.id,
    name = this.name,
    email = this.email
)

fun MobileNetV1ModelResult.toMobileNetModelResponse(processedBitmap: Bitmap?) =
    MobileNetModelResponse(
        processedBitmap = processedBitmap,
        category = this.category ?: "unrecognized"
    )