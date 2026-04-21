package com.ElOuedUniv.maktaba.presentation.book.add

sealed interface AddBookUiAction {
    data class OnTitleChange(val title: String) : AddBookUiAction
    data class OnIsbnChange(val isbn: String) : AddBookUiAction
    data class OnPagesChange(val pages: String) : AddBookUiAction
    data class OnCategoryChange(val categoryId: String) : AddBookUiAction
    data class OnImageUrlChange(val url: String) : AddBookUiAction
    object OnAddClick : AddBookUiAction
}
