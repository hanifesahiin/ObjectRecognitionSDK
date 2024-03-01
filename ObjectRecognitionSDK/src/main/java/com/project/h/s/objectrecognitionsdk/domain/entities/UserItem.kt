package com.project.h.s.objectrecognitionsdk.domain.entities

data class UserItem(
    val id: Int,
    val name: String = "",
    val email: String = "",
    val company: String = "",
    val address: String = "",
    val zip: String = "",
    val state: String = "",
    val country: String = "",
    val phone: String = "",
    val photo: String = ""
)
