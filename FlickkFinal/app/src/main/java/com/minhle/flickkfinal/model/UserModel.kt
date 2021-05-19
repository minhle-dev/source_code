package com.minhle.flickkfinal.model

data class UserModel(
    val userId: String,
    val imageUrl: String,
    val username: String,
    val email: String,
    val password: String,
    val fingerAuth: Boolean
)
