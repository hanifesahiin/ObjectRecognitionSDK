package com.project.h.s.objectrecognitionsdk.domain.usecases

import android.graphics.Bitmap
import com.project.h.s.objectrecognitionsdk.domain.entities.MobileNetModelResponse
import com.project.h.s.objectrecognitionsdk.domain.mapper.toMobileNetModelResponse
import com.project.h.s.objectrecognitionsdk.domain.repository.MobileNetV1Model

class MobileNetV1UseCase(private val mobileNetV1Model: MobileNetV1Model) {
    suspend operator fun invoke(bitmap: Bitmap): MobileNetModelResponse {
        val processedBitmap = mobileNetV1Model.processBitmap(bitmap)
        return mobileNetV1Model.results().toMobileNetModelResponse(processedBitmap)
    }

    fun closeModel() {
        mobileNetV1Model.closeModel()
    }
}