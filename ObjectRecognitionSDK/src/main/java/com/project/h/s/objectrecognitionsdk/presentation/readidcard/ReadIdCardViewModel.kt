package com.project.h.s.objectrecognitionsdk.presentation.readidcard

import android.nfc.Tag
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.h.s.objectrecognitionsdk.domain.usecases.NfcReaderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadIdCardViewModel @Inject constructor(
    private val nfcReaderUseCase: NfcReaderUseCase
) : ViewModel() {
    val _uiFlow = MutableStateFlow(UiFlow())
    val uiFlow = _uiFlow.asStateFlow()

    fun connect(tag: Tag) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = nfcReaderUseCase.invoke(tag)
            _uiFlow.update {
                it.copy(name = response.name, lastName = response.lastName)
            }
        }
    }

    fun hideAlertContainer() {
        _uiFlow.update {
            it.copy(alert = false, alertMessage = "")
        }
    }

    fun showAlertContainer(alert: String) {
        _uiFlow.update {
            it.copy(alert = true, alertMessage = alert)
        }
    }

    data class UiFlow(
        val name: String = "hanife",
        val lastName: String = "şahin",
        val alert: Boolean = false,
        val alertMessage: String = ""
    )
}