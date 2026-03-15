package com.ElOuedUniv.maktaba.presentation.category

import com.ElOuedUniv.maktaba.data.model.Category

/**
 * UI State for the Category list screen.
 * TODO: Student must complete this data class.
 */
data class CategoryUiState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
