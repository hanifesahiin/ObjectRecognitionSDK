package com.project.h.s.objectrecognitionsdk.presentation.captureimage

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.h.s.objectrecognitionsdk.domain.usecases.MobileNetV1UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CaptureImageViewModel @Inject constructor(private val mobileNetV1UseCase: MobileNetV1UseCase) :
    ViewModel() {

    val _uiFlow = MutableStateFlow(UiFlow())
    val uiFlow = _uiFlow.asStateFlow()
    fun tensorFlow(bitmap: Bitmap) {
        _uiFlow.update {
            it.copy(progressIndicator = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            val mobileNetModelResponse = mobileNetV1UseCase.invoke(bitmap)

            _uiFlow.update {
                it.copy(
                    progressIndicator = false,
                    progressedImage = mobileNetModelResponse.processedBitmap,
                    processedResult = mobileNetModelResponse.category
                )
            }
        }
    }

    data class UiFlow(
        val progressIndicator: Boolean = false,
        val progressedImage: Bitmap? = null,
        val processedResult: String = ""
    )

    @Override
    override fun onCleared() {
        super.onCleared()
        mobileNetV1UseCase.closeModel()
    }
}
