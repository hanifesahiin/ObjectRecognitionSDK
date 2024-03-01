package com.project.h.s.objectrecognitionsdk.domain.repository

import com.project.h.s.objectrecognitionsdk.domain.model.LoginUserModel
import com.project.h.s.objectrecognitionsdk.data.entities.LoginUserResponse
import com.project.h.s.objectrecognitionsdk.data.entities.UserItemResponse
import kotlinx.coroutines.flow.Flow

interface ApiCallRepository {
    fun login(body: LoginUserModel?): Flow<LoginUserResponse>
    fun allUser(token: String): Flow<List<UserItemResponse>>
}