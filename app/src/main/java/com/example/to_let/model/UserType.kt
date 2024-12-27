package com.example.to_let.model

// Data classes representing User Type
sealed class UserType {
    abstract val name: String
    abstract val email: String
    abstract val phone: String
    abstract val address: String
    abstract val location: String
}

