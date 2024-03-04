package com.project.h.s.objectrecognitionsdk.domain.model

import com.google.gson.annotations.SerializedName

data class LoginUserModel(
    @SerializedName("username")
    var userName: String = "",
    @SerializedName("password")
    var password: String = ""
)