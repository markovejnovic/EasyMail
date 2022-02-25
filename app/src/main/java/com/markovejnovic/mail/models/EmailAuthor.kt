package com.markovejnovic.mail.models

import androidx.compose.ui.graphics.ImageBitmap

data class EmailAuthor(
    val name: String,
    val image: ImageBitmap,
    val address: String)
