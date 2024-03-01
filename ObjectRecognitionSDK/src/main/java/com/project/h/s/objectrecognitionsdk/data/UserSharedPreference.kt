package com.project.h.s.objectrecognitionsdk.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import java.nio.charset.Charset

class UserSharedPreference(val context: Context) {
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)

    private val tokenAuth = "token"
    private val userName = "userName"
    private val userPass = "userPass"
    private val signinChecked = "signinChecked"

    fun saveToken(value: String) {
        sharedPref.edit()
            .putString(tokenAuth, value)
            .apply()
    }
    private fun saveUserName(value: String) {
        sharedPref.edit()
            .putString(userName, value)
            .apply()
    }
    private fun savePassword(dataToEncrypt: String) {
        val encodedString = Base64.encodeToString(dataToEncrypt.toByteArray(Charset.forName("UTF-8")), Base64.DEFAULT)
        Log.d("password GET data2: ", encodedString)
        sharedPref.edit()
            .putString(userPass, encodedString)
            .apply()
    }

    fun saveSignInChecked(value: Boolean, userName: String, password: String) {
        if (value) {
            sharedPref.edit()
                .putBoolean(signinChecked, true)
                .apply()
            saveUserName(userName)
            savePassword(password)
        } else {
            clearSignInScreenParameters()
        }
    }

    fun getToken(): String = sharedPref.getString(tokenAuth, "") ?: ""
    fun getUserName(): String = sharedPref.getString(userName, "") ?: ""
    fun getPassword(): String {
        val encodedString = sharedPref.getString(userPass, "") ?: ""

        val a = if (encodedString.isNotEmpty()) Base64.decode(encodedString, Base64.DEFAULT).toString(Charset.forName("UTF-8")) else ""
        Log.d("password GET Gelen: ", a)
        return a
    }
    fun getChecked(): Boolean = sharedPref.getBoolean(signinChecked, false)

    private fun clearSignInScreenParameters() {
        sharedPref.edit()
            .putString(userName, "")
            .putString(userPass, "")
            .putBoolean(signinChecked, false)
            .apply()
    }
}