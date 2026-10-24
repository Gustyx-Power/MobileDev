package com.gdgoc.luminawalls.data.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable

@Immutable
data class Wallpaper(
    val id: String,
    val title: String,
    val author: String,
    val category: String,
    val imageUrl: String = "",
    val thumbUrl: String = imageUrl,
    val downloadUrl: String = imageUrl,
    @param:DrawableRes val drawableResId: Int = 0,
    val tags: List<String> = emptyList(),
)

@Immutable
data class Contributor(
    val name: String,
    val role: String,
    val github: String,
    val bio: String,
    val avatarLetter: String = name.take(1).uppercase(),
    val avatarUrl: String? = null,
)
