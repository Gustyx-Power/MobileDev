package com.gdgocwidyatama.sukiwalls.data.model

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
