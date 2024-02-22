package com.project.h.s.objectrecognitionsdkapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.h.s.objectrecognitionsdk.ObjectRecognitionSDKInterface
import com.project.h.s.objectrecognitionsdkapplication.ui.theme.ObjectRecognitionSDKApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ObjectRecognitionSDKActivity : ComponentActivity() {

    @Inject
    lateinit var objectRecognitionSDKInterface: ObjectRecognitionSDKInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ObjectRecognitionSDKApplicationTheme {
                SdkInitializeScreen(objectRecognitionSDKInterface)
            }
        }
    }
}

@Composable
fun SdkInitializeScreen(objectRecognitionSDKInterface: ObjectRecognitionSDKInterface? = null) {
    val context = LocalContext.current

    Box(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(20.dp)
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                .align(Alignment.Center),
            onClick = {
                objectRecognitionSDKInterface?.initialize(context = context)
            }
        ) {
            Text(color = MaterialTheme.colorScheme.onPrimary, text = "Initialize SDK")
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun Preview() {
    ObjectRecognitionSDKApplicationTheme {
        SdkInitializeScreen()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewNight() {
    ObjectRecognitionSDKApplicationTheme {
        SdkInitializeScreen()
    }
}

