package com.project.h.s.objectrecognitionsdk.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.RectF
import com.project.h.s.objectrecognitionsdk.data.entities.MobileNetV1ModelResult
import com.project.h.s.objectrecognitionsdk.domain.repository.MobileNetV1Model
import com.project.h.s.objectrecognitionsdk.ml.MobilenetV1
import com.project.h.s.objectrecognitionsdk.utils.Logger
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import javax.inject.Inject

class MobileNetV1ModelImpl @Inject constructor(val context: Context) : MobileNetV1Model {
    private val _mobileNetV1ModelClass = "MobileNetV1Model"

    private var model: MobilenetV1? = null
    private val lock: Lock = ReentrantLock()
    private val imageSize = 300

    private var location: RectF? = null
    private var score: Float? = null
    private var category: String? = null

    override suspend fun processBitmap(selectedImage: Bitmap): Bitmap? {
        model = MobilenetV1.newInstance(context)
        return try {
            model = MobilenetV1.newInstance(context)

            val tensorImage = TensorImage(DataType.FLOAT32)

            resizedPixels(selectedImage)?.let { inputPixels ->
                tensorImage.load(inputBitmap(inputPixels))
            }

            model?.let {
                lock.lock()
                try {
                    outputs(it.process(tensorImage))
                } finally {
                    lock.unlock()
                }
            }

            model?.close()
            tensorImage.bitmap
        } catch (e: Exception) {
            Logger.e(_mobileNetV1ModelClass, "mobileNetv1Model", e)
            null
        }
    }

    override fun results(): MobileNetV1ModelResult {
        return MobileNetV1ModelResult(
            location = this.location,
            score = this.score,
            category = this.category
        )
    }

    private fun inputBitmap(pixelArray: IntArray?): Bitmap? {
        if (pixelArray == null) {
            return null
        }

        return try {
            Bitmap.createBitmap(pixelArray, imageSize, imageSize, Bitmap.Config.ARGB_8888)
        } catch (e: Exception) {
            Logger.e(_mobileNetV1ModelClass, "inputBitmap", e)
            null
        }
    }

    private fun resizedPixels(selectedImage: Bitmap): IntArray? {
        return try {
            val resizedImage: Bitmap = Bitmap.createScaledBitmap(
                selectedImage,
                imageSize,
                imageSize,
                true
            )

            val intValues = IntArray(imageSize * imageSize)
            resizedImage.getPixels(
                intValues,
                0,
                resizedImage.width,
                0,
                0,
                resizedImage.width,
                resizedImage.height
            )

            intValues
        } catch (e: Exception) {
            Logger.e(_mobileNetV1ModelClass, "resizedPixels", e)
            null
        }
    }

    private fun outputs(process: MobilenetV1.Outputs) {
        return try {
            val detectionResult = process.detectionResultList.get(0)

            location = detectionResult.locationAsRectF
            category = detectionResult.categoryAsString
            score = detectionResult.scoreAsFloat
        } catch (e: Exception) {
            Logger.e(_mobileNetV1ModelClass, "outputs", e)
        }
    }

    override fun closeModel() {
        model?.close()
    }
}