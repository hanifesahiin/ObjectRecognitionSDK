package com.project.h.s.objectrecognitionsdk.presentation.captureimage

import android.graphics.Bitmap
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.project.h.s.objectrecognitionsdk.R
import com.project.h.s.objectrecognitionsdk.theme.Dimension
import com.project.h.s.objectrecognitionsdk.theme.ROUND_20
import com.project.h.s.objectrecognitionsdk.theme.greyExtraLight
import com.project.h.s.objectrecognitionsdk.theme.typography
import com.project.h.s.objectrecognitionsdk.utils.TestTags

@Composable
fun CameraScreen(
    progressIndicator: Boolean = false,
    progressedImage: Bitmap?,
    processedResult: String = "",
    capturedImage: (Bitmap) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .testTag(TestTags.camera_screen_component)
    ) {
        AndroidViewCapture(progressIndicator, capturedImage)
        ProcessedObject(progressedImage, processedResult)
    }
}

@Composable
fun PermissionScreen(requestPermission: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimension.margin_20)
            .testTag(TestTags.camera_permission_screen_component),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(bottom = Dimension.margin_10),
            text = stringResource(id = R.string.user_camera_description)
        )
        Button(onClick = requestPermission) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_photo_camera_24),
                contentDescription = stringResource(id = R.string.camera)
            )
            Text(text = stringResource(id = R.string.allow))
        }
    }
}

@Composable
fun AndroidViewCapture(progressIndicator: Boolean, capturedImage: (Bitmap) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController = remember { LifecycleCameraController(context) }
    Box(
        modifier = Modifier
            .height(
                (LocalConfiguration.current.screenHeightDp * 0.7f).dp
            )
            .fillMaxSize()
            .background(Color.Black)
            .testTag(TestTags.android_view_capture)
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = { context ->
                PreviewView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    scaleType = PreviewView.ScaleType.FILL_START
                }.also {
                    it.controller = cameraController
                    cameraController.bindToLifecycle(lifecycleOwner)
                }
            }
        )

        ExtendedFloatingActionButton(
            modifier = Modifier
                .width(Dimension.width_150)
                .height(Dimension.height_60)
                .align(Alignment.TopEnd)
                .padding(top = Dimension.margin_15, end = Dimension.margin_20)
                .testTag(TestTags.complete_button),
            text = {
                Text(
                    text = stringResource(id = R.string.complete),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    style = typography.bodyMedium
                )
            },
            icon = { },
            shape = RoundedCornerShape(0, ROUND_20, 0, ROUND_20),
            containerColor = Color.Black,
            onClick = {
                val mainExecutor = ContextCompat.getMainExecutor(context)
                cameraController.takePicture(
                    mainExecutor,
                    object : ImageCapture.OnImageCapturedCallback() {
                        override fun onCaptureSuccess(capturedImage: ImageProxy) {
                            capturedImage(capturedImage.toBitmap())
                        }
                    }
                )
            }
        )
        if (progressIndicator) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun ProcessedObject(progressedImage: Bitmap?, processedResult: String) {
    Box(
        modifier = Modifier
            .height(
                (LocalConfiguration.current.screenHeightDp * 0.3f).dp
            )
            .fillMaxSize()
            .padding(
                top = Dimension.margin_15,
                bottom = Dimension.margin_15,
                start = Dimension.margin_20,
                end = Dimension.margin_20
            )
            .background(greyExtraLight)
            .testTag(TestTags.processed_object)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.captured_object),
            color = Color.Black,
            textAlign = TextAlign.Center,
            style = typography.bodyLarge
        )

        progressedImage?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(TestTags.processed_image)
            )
            Text(
                modifier = Modifier.align(Alignment.BottomCenter),
                text = processedResult,
                color = Color.Red
            )
        }
    }
}