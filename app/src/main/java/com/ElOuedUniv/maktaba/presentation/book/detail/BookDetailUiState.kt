package com.ElOuedUniv.maktaba.presentation.book.detail

import com.ElOuedUniv.maktaba.data.model.Book

data class BookDetailUiState(
    val book: Book? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
