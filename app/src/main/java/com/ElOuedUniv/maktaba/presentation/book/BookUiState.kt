package com.ElOuedUniv.maktaba.presentation.book

import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.data.model.Category

data class BookUiState(
    val books: List<Book> = emptyList(),
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isAddingBook: Boolean = false
)
