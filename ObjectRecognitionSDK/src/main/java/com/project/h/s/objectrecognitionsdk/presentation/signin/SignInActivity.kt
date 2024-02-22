package com.project.h.s.objectrecognitionsdk.presentation.signin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SignInScreen()
        }
    }
}

@Composable
fun SignInScreen() {
    val context = LocalContext.current as? SignInActivity

    val checkbox = rememberSaveable {
        mutableStateOf(false)
    }
    Surface {
        SignInBox(checkbox.value, onCheckedChange = {
            checkbox.value = it
        }, onSignInAction = {
            context?.let {
                it.startUserListActivity()
                it.finish()
            }
        })
    }
}

@Preview
@Composable()
fun Preview() {
    SignInScreen()
}
