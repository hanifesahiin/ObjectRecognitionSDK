package com.project.h.s.objectrecognitionsdk.domain.usecases

import com.project.h.s.objectrecognitionsdk.data.UserSharedPreference
import com.project.h.s.objectrecognitionsdk.data.entities.UserItemResponse
import com.project.h.s.objectrecognitionsdk.domain.entities.ApiResponse
import com.project.h.s.objectrecognitionsdk.domain.entities.UserItem
import com.project.h.s.objectrecognitionsdk.domain.mapper.toUserItem
import com.project.h.s.objectrecognitionsdk.domain.repository.ApiCallRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

class UserListUseCase(
    private val apiCallRepository: ApiCallRepository,
    private val sharedPreference: UserSharedPreference
) {
    suspend operator fun invoke(): Flow<List<UserItem>> {
        val response = apiCallRepository.allUser(sharedPreference.getToken()).single()
        return flow {
            when (response) {
                is ApiResponse.Success -> {
                    emit(
                        (response.data as List<UserItemResponse>).map { it.toUserItem() }
                    )
                }

                is ApiResponse.Error -> {
                    emit(emptyList())
                }
            }
        }
    }
}