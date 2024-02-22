package com.project.h.s.objectrecognitionsdk.presentation.userlist

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserListScreen()
        }
    }
}

@Composable
fun UserListScreen() {
}

@Preview(uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun Review() {
}