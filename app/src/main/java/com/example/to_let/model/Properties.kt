package com.example.to_let.model

import android.media.Image

data class Properties(
    val id : String,
    val address: String,
    val rentalAmount : String,
    val occupied : Boolean = false,
    val images : List<Image>
)
