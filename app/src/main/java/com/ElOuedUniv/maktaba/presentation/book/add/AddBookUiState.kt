package com.ElOuedUniv.maktaba.presentation.book.add

import com.ElOuedUniv.maktaba.data.model.Category

data class AddBookUiState(
    val title: String = "",
    val isbn: String = "",
    val nbPages: String = "",
    val imageUrl: String = "",
    val categoryId: String? = null,
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isFormValid: Boolean = false,
    val titleError: String? = null,
    val isbnError: String? = null,
    val nbPagesError: String? = null,
    val isEditMode: Boolean = false,
    val errorMessage: String? = null
)
