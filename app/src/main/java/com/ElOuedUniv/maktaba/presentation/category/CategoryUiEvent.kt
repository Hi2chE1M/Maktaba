package com.ElOuedUniv.maktaba.presentation.category

/**
 * One-time UI events (System events) for the Category screen.
 * TODO: Student must use this for effects.
 */
sealed interface CategoryUiEvent {
    data class ShowSnackbar(val message: String) : CategoryUiEvent
    object NavigateBack : CategoryUiEvent
}
