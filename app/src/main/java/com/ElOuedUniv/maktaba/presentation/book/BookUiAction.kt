package com.ElOuedUniv.maktaba.presentation.book

import com.ElOuedUniv.maktaba.data.model.Book

sealed interface BookUiAction {
    object RefreshBooks : BookUiAction
    data class OnSearchQueryChange(val query: String) : BookUiAction
    data class OnCategoryFilterSelect(val categoryId: String?) : BookUiAction
    data class OnDeleteBook(val isbn: String) : BookUiAction
    data class OnUpdateBook(val book: Book) : BookUiAction
}
