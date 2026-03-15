package com.ElOuedUniv.maktaba.presentation.book

sealed interface BookUiEvent {
    data class ShowSnackbar(val message: String) : BookUiEvent
    object NavigateToCategories : BookUiEvent
}