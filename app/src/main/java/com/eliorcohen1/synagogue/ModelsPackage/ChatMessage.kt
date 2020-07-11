package com.eliorcohen1.synagogue.ModelsPackage

import java.util.*

data class ChatMessage(
        val text: String,
        val email: String,
        val user: String,
        val timestamp: Date
)
