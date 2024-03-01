package com.project.h.s.objectrecognitionsdk.presentation.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.h.s.objectrecognitionsdk.domain.entities.UserItem
import com.project.h.s.objectrecognitionsdk.domain.usecases.SharedPreferenceUseCase
import com.project.h.s.objectrecognitionsdk.domain.usecases.UserListUseCase
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
class UserListViewModel @Inject constructor(
    private val userListUseCase: UserListUseCase,
    private val sharedPreferenceUseCase: SharedPreferenceUseCase
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UserEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val _uiFlow = MutableStateFlow(UiFlow())
    val uiFlow = _uiFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            userListUseCase().collect { userList ->
                _uiFlow.update {
                    it.copy(userList = userList, progressIndicator = false)
                }
            }
        }
    }

    fun logout() {
        sharedPreferenceUseCase.clearToken()
    }

    fun confirm(id: Int) {
        viewModelScope.launch {
            if (id % 2 == 0) {
                _eventFlow.emit(UserEvent.Even)
            } else {
                _eventFlow.emit(UserEvent.Odd)
            }
        }
    }

    sealed class UserEvent {
        data object Odd : UserEvent()
        data object Even : UserEvent()
    }

    data class UiFlow(
        val userList: List<UserItem> = emptyList(),
        val progressIndicator: Boolean = true
    )
}