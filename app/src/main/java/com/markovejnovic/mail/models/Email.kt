package com.markovejnovic.mail.models

data class Email(
    val subject: String,
    val author: EmailAuthor,
    val short: String)
