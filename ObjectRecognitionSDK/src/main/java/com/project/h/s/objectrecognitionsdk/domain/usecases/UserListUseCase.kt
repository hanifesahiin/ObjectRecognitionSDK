package com.project.h.s.objectrecognitionsdk.domain.usecases

import com.project.h.s.objectrecognitionsdk.data.UserSharedPreference
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
        val list = apiCallRepository.allUser(sharedPreference.getToken()).single()
        return flow {
            emit(
                list.map { it.toUserItem() }
            )
        }
    }
}