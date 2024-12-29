package com.example.to_let.model

data class Tenant(
    val name: String,
    val propertyType: String, // e.g., "PG", "House", "Hotel", "Room"
    val rentPaid: Boolean
)