package com.ElOuedUniv.maktaba.presentation.book

import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.data.model.Category

/**
 * UI State for the Book list screen.
 */
data class BookUiState(
    val books: List<Book> = emptyList(),
    val filteredBooks: List<Book> = emptyList(),
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val selectedCategoryId: String? = null,
    val isAddingBook: Boolean = false
)
