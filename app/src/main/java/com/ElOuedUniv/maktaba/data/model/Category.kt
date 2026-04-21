package com.ElOuedUniv.maktaba.data.model

import androidx.annotation.DrawableRes

/**
 * Data class representing a book category.
 * Updated as part of TP4 to include iconRes.
 */
data class Category(
    val id: String,
    val name: String,
    val description: String,
    @DrawableRes val iconRes: Int = android.R.drawable.ic_menu_agenda
)
