package com.project.h.s.objectrecognitionsdk.presentation.signin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.project.h.s.objectrecognitionsdk.theme.Dimension
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

@AndroidEntryPoint
class SignInActivity : ComponentActivity() {

    private val signInViewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SignInScreen(
                signInViewModel.uiFlow,
                signInViewModel.eventFlow,
                signInViewModel::setChecked,
                signInViewModel::signIn,
                signInViewModel::setUserName,
                signInViewModel::setPassword,
                signInViewModel::hideErrorContainer
            )
        }
    }
}

@Composable
fun SignInScreen(
    uiFlow: StateFlow<SignInViewModel.UIFlow>,
    eventFlow: SharedFlow<SignInViewModel.SignInEvent>,
    setChecked: (Boolean) -> Unit = {},
    signIn: () -> Unit = {},
    setUserName: (String) -> Unit = {},
    setPassword: (String) -> Unit = {},
    hideErrorContainer: () -> Unit = {}
) {
    val context = LocalContext.current as? SignInActivity
    val state = uiFlow.collectAsState()

    LaunchedEffect(true) {
        eventFlow.collect { event ->
            when (event) {
                is SignInViewModel.SignInEvent.Success -> {
                    context?.let {
                        it.startUserListActivity()
                        it.finish()
                    }
                }
            }
        }
    }

    Surface {
        Box(modifier = Modifier.fillMaxSize()) {
            SignInBox(
                state = state.value,
                onCheckedChange = {
                    setChecked(it)
                },
                onSignInAction = {
                    signIn()
                },
                onEmailChange = {
                    setUserName(it)
                },
                onPasswordChange = {
                    setPassword(it)
                }
            )

            if (state.value.error) {
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = Dimension.margin_20),
                    text = state.value.errorMessage
                )

                LaunchedEffect(state.value.error) {
                    delay(2000)
                    hideErrorContainer()
                }
            }
        }
    }
}

@Preview
@Composable()
fun Preview() {
    SignInScreen(
        uiFlow = MutableStateFlow(SignInViewModel.UIFlow()),
        eventFlow = MutableSharedFlow()
    )
}
