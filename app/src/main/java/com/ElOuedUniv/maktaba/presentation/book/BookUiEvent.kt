package com.ElOuedUniv.maktaba.presentation.book

sealed interface BookUiEvent {
    data class ShowMessage(val message: String) : BookUiEvent
}
