package com.project.h.s.objectrecognitionsdk.presentation.userlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.project.h.s.objectrecognitionsdk.R
import com.project.h.s.objectrecognitionsdk.domain.entities.UserItem
import com.project.h.s.objectrecognitionsdk.theme.Dimension
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

@AndroidEntryPoint
class UserListActivity : ComponentActivity() {

    val userListViewModel: UserListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserListScreen(
                userListViewModel.uiFlow,
                userListViewModel.eventFlow,
                userListViewModel::confirm,
                userListViewModel::logout
            )
        }
    }
}

@Composable
fun UserListScreen(
    uiFlow: StateFlow<UserListViewModel.UiFlow>,
    eventFlow: SharedFlow<UserListViewModel.UserEvent>,
    confirm: (Int) -> Unit = {},
    logout: () -> Unit = {}
) {
    val context = LocalContext.current as? UserListActivity
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val state by uiFlow.collectAsState()

    LaunchedEffect(true) {
        eventFlow.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect { event ->
            when (event) {
                is UserListViewModel.UserEvent.Even -> {
                    context?.startReadIdCardActivity()
                }

                else -> {
                    context?.startCaptureImageActivity()
                }
            }
        }
    }

    Surface {
        Box {
            UserListContainer(state.userList, progressIndicator = state.progressIndicator) {
                confirm(it)
            }
            Button(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = Dimension.margin_10, bottom = Dimension.margin_10),
                onClick = {
                    logout()
                    context?.finish()
                }
            ) {
                Text(text = stringResource(id = R.string.logout))
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    UserListScreen(
        uiFlow = MutableStateFlow(
            UserListViewModel.UiFlow(
                userList = listOf(
                    UserItem(
                        id = 1,
                        name = "Michael",
                        email = "michael@mailinator.com"
                    )
                )
            )
        ),
        eventFlow = MutableSharedFlow()
    )
}