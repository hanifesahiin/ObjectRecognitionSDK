package com.project.h.s.objectrecognitionsdk.data.entities

import com.google.gson.annotations.SerializedName

data class LoginUserResponse(
    @SerializedName("success")
    val success: Boolean = false,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("token")
    val token: String = "",
    @SerializedName("error")
    val error: Error = Error()
)

data class Error(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("message")
    val message: String = ""
)