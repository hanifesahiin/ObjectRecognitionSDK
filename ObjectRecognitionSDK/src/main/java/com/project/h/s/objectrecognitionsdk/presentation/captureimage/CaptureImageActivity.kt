package com.project.h.s.objectrecognitionsdk.presentation.captureimage

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow

@AndroidEntryPoint
class CaptureImageActivity : ComponentActivity() {
    private val captureImageViewModel: CaptureImageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaptureImageScreen(
                captureImageViewModel.uiFlow,
                captureImageViewModel::tensorFlow
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CaptureImageScreen(
    uiFlow: StateFlow<CaptureImageViewModel.UiFlow>,
    tensorFlow: (Bitmap) -> Unit = {}
) {
    val state by uiFlow.collectAsState()

    val cameraPermissionState: PermissionState =
        rememberPermissionState(android.Manifest.permission.CAMERA)

    if (cameraPermissionState.status.isGranted) {
        CameraScreen(
            progressIndicator = state.progressIndicator,
            progressedImage = state.progressedImage,
            processedResult = state.processedResult
        ) {
            tensorFlow(it)
        }
    } else {
        PermissionScreen(cameraPermissionState::launchPermissionRequest)
    }
}