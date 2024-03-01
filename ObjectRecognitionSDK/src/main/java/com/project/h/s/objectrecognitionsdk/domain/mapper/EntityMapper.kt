package com.project.h.s.objectrecognitionsdk.domain.mapper

import com.project.h.s.objectrecognitionsdk.data.entities.UserItemResponse
import com.project.h.s.objectrecognitionsdk.domain.entities.UserItem

fun UserItemResponse.toUserItem() = UserItem(
    id = this.id,
    name = this.name,
    email = this.email
)