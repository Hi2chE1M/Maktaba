package com.ElOuedUniv.maktaba.presentation.book.detail

sealed class BookDetailUiAction {
    object OnBackClick : BookDetailUiAction()
    object OnDeleteClick : BookDetailUiAction()
    object OnEditClick : BookDetailUiAction()
    object OnReadClick : BookDetailUiAction()
    object OnFavoriteClick : BookDetailUiAction()
}
