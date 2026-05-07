package com.ElOuedUniv.maktaba.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: String? = null,
    val name: String,
    val description: String? = null
)
