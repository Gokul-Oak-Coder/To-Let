package com.example.to_let.model

data class UserProfile(
    val imageUrl: Int,
    val username: String,
    val phoneNumber: String,
    val email : String? = null,
    val address: String? = null,
    val location: String,
    val knownLanguages: List<String>,
    val dob: String
)
