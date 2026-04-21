package com.ElOuedUniv.maktaba.presentation.book.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ElOuedUniv.maktaba.domain.usecase.GetBookByIsbnUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getBookByIsbnUseCase: GetBookByIsbnUseCase
) : ViewModel() {

    private val isbn: String = checkNotNull(savedStateHandle["isbn"])
    
    private val _uiState = MutableStateFlow(BookDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadBook()
    }

    private fun loadBook() {
        _uiState.update { it.copy(isLoading = true) }
        val book = getBookByIsbnUseCase(isbn)
        _uiState.update { it.copy(isLoading = false, book = book) }
    }

    fun onAction(action: BookDetailUiAction) {
        // Handle actions like "Retry" or "Refresh" if added later
    }
}
