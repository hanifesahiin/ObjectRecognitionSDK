package com.project.h.s.objectrecognitionsdk.domain.repository

import com.project.h.s.objectrecognitionsdk.data.entities.LoginUserResponse
import com.project.h.s.objectrecognitionsdk.domain.entities.ApiResponse
import com.project.h.s.objectrecognitionsdk.domain.model.LoginUserModel
import kotlinx.coroutines.flow.Flow

interface ApiCallRepository {
    fun login(body: LoginUserModel?): Flow<LoginUserResponse>
    fun allUser(token: String): Flow<ApiResponse<Any>>
}