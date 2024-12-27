package com.example.to_let.model

data class Owner(
    val name: String,
    val email: String,
    val phone: String,
    val languages: List<String>
)