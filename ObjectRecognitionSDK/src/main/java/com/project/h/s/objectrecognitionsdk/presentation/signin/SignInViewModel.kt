package com.project.h.s.objectrecognitionsdk.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.h.s.objectrecognitionsdk.domain.entities.ApiResponse
import com.project.h.s.objectrecognitionsdk.domain.entities.LoginSuccess
import com.project.h.s.objectrecognitionsdk.domain.model.LoginUserModel
import com.project.h.s.objectrecognitionsdk.domain.usecases.LoginUseCase
import com.project.h.s.objectrecognitionsdk.domain.usecases.SharedPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val sharedPreferenceUseCase: SharedPreferenceUseCase
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<SignInEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _uiFlow = MutableStateFlow(UIFlow())
    val uiFlow = _uiFlow.asStateFlow()

    init {
        updateSigninUserParameters()
    }

    private fun updateSigninUserParameters() {
        viewModelScope.launch {
            _uiFlow.update {
                it.copy(
                    userName = sharedPreferenceUseCase.getUserName(),
                    password = sharedPreferenceUseCase.getPassword(),
                    checked = sharedPreferenceUseCase.getChecked()
                )
            }
        }
    }

    fun signIn() {
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase.invoke(
                LoginUserModel(
                    userName = uiFlow.value.userName,
                    password = uiFlow.value.password
                )
            ).collect { result ->
                when (result) {
                    is ApiResponse.Success -> {
                        sharedPreferenceUseCase.saveSignIn(
                            uiFlow.value.checked,
                            uiFlow.value.userName,
                            uiFlow.value.password,
                            (result.data as LoginSuccess).token
                        )

                        _eventFlow.emit(SignInEvent.Success)
                    }

                    is ApiResponse.Error -> {
                        _uiFlow.update {
                            it.copy(error = true, errorMessage = result.errorMessage)
                        }
                    }
                }
            }
        }
    }

    fun hideErrorContainer() {
        _uiFlow.update {
            it.copy(error = false)
        }
    }

    fun setChecked(value: Boolean) {
        _uiFlow.update {
            it.copy(checked = value)
        }
    }

    fun setUserName(value: String) {
        _uiFlow.update {
            it.copy(userName = value)
        }
    }

    fun setPassword(value: String) {
        _uiFlow.update {
            it.copy(password = value)
        }
    }

    sealed class SignInEvent {
        data object Success : SignInEvent()
    }

    data class UIFlow(
        val userName: String = "",
        val password: String = "",
        val checked: Boolean = false,
        val error: Boolean = false,
        val errorMessage: String = ""
    )
}