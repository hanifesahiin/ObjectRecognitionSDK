package com.project.h.s.objectrecognitionsdk.data.api

import com.project.h.s.objectrecognitionsdk.data.entities.LoginUserResponse
import com.project.h.s.objectrecognitionsdk.domain.model.LoginUserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface JsonPlaceHolderApiCall {

    @POST("/login")
    suspend fun login(@Body body: LoginUserModel): Response<LoginUserResponse>
}