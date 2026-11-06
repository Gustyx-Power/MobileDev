package com.gdgocwidyatama.sukiwalls.data.model

import androidx.compose.runtime.Immutable

@Immutable
data class Contributor(
    val name: String,
    val role: String,
    val github: String,
    val bio: String,
    val avatarLetter: String = name.take(1).uppercase(),
    val avatarUrl: String? = null,
)
