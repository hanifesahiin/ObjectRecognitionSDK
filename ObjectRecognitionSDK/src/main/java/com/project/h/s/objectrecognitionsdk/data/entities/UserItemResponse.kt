package com.project.h.s.objectrecognitionsdk.data.entities

import com.google.gson.annotations.SerializedName

data class UserItemResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("company")
    val company: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("zip")
    val zip: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("photo")
    val photo: String,
)