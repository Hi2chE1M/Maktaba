package com.ElOuedUniv.maktaba.presentation.book.add

import android.net.Uri

sealed class AddBookUiAction {
    data class OnTitleChange(val title: String) : AddBookUiAction()
    data class OnIsbnChange(val isbn: String) : AddBookUiAction()
    data class OnPagesChange(val pages: String) : AddBookUiAction()
    data class OnDescriptionChange(val description: String) : AddBookUiAction()
    data class OnCategoryChange(val categoryId: String?) : AddBookUiAction()
    data class OnPdfSelected(val uri: Uri?, val name: String?) : AddBookUiAction()
    data class OnFavoriteToggle(val isFavorite: Boolean) : AddBookUiAction()
    object OnAddClick : AddBookUiAction()
    data class OnCoverChange(val uri: Uri?) : AddBookUiAction()
}
