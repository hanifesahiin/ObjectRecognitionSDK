package com.project.h.s.objectrecognitionsdk.fakemodule.model

import android.graphics.Bitmap
import com.project.h.s.objectrecognitionsdk.data.entities.MobileNetV1ModelResult
import com.project.h.s.objectrecognitionsdk.domain.repository.MobileNetV1Model

class FakeMobileNetV1ModelImpl : MobileNetV1Model {
    override suspend fun processBitmap(selectedImage: Bitmap): Bitmap? {
        return Bitmap.createBitmap(intArrayOf(), 300, 300, Bitmap.Config.ARGB_8888)
    }

    override fun results(): MobileNetV1ModelResult {
        return MobileNetV1ModelResult(
            null,
            "apple",
            null
        )
    }

    override fun closeModel() {}
}