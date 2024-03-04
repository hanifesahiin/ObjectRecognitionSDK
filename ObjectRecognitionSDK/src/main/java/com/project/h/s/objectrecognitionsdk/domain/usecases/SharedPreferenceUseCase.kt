package com.project.h.s.objectrecognitionsdk.domain.usecases

import com.project.h.s.objectrecognitionsdk.data.UserSharedPreference

class SharedPreferenceUseCase(private val sharedPreference: UserSharedPreference) {
    fun saveSignIn(checked: Boolean, userName: String, password: String, token: String) {
        sharedPreference.saveSignInChecked(
            checked,
            userName,
            password
        )
        sharedPreference.saveToken(token)
    }

    fun getUserName() = sharedPreference.getUserName()
    fun getPassword() = sharedPreference.getPassword()
    fun getChecked() = sharedPreference.getChecked()
    fun clearToken() {
        sharedPreference.saveToken("")
    }
}