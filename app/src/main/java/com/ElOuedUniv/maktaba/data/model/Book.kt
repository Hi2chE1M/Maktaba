package com.ElOuedUniv.maktaba.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val isbn: String,
    val title: String,
    val nbPages: Int,
    val description: String? = null,
    val imageUrl: String? = null,
    val pdfUrl: String? = null,
    val categoryId: String? = null,
    val isFavorite: Boolean = false
)
