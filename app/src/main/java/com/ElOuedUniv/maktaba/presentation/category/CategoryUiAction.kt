package com.ElOuedUniv.maktaba.presentation.category

/**
 * UI Actions representing user interactions on the Category screen.
 * TODO: Student must implement and use these actions.
 */
sealed interface CategoryUiAction {
    object RefreshCategories : CategoryUiAction
    object OnBackClick : CategoryUiAction
}
