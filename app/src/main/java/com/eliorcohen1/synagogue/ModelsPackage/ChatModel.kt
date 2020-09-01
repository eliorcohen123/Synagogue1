package com.eliorcohen1.synagogue.ModelsPackage

import java.util.*

data class ChatModel(
        val text: String,
        val email: String,
        val user: String,
        val timestamp: Date
)
