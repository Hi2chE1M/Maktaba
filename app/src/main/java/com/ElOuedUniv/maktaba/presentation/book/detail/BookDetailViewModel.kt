package com.ElOuedUniv.maktaba.presentation.book.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ElOuedUniv.maktaba.domain.usecase.GetBookByIsbnUseCase
import com.ElOuedUniv.maktaba.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getBookByIsbnUseCase: GetBookByIsbnUseCase,
    private val bookRepository: BookRepository
) : ViewModel() {

    private val isbn: String = checkNotNull(savedStateHandle["isbn"])
    
    private val _uiState = MutableStateFlow(BookDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadBook()
    }

    private fun loadBook() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val book = getBookByIsbnUseCase(isbn)
                _uiState.update { it.copy(isLoading = false, book = book) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun onAction(action: BookDetailUiAction) {
        when (action) {
            BookDetailUiAction.OnDeleteClick -> deleteBook()
            BookDetailUiAction.OnFavoriteClick -> toggleFavorite()
            else -> {}
        }
    }

    private fun toggleFavorite() {
        viewModelScope.launch {
            try {
                bookRepository.toggleFavorite(isbn)
                // Refresh local state
                val updatedBook = _uiState.value.book?.let { it.copy(isFavorite = !it.isFavorite) }
                _uiState.update { it.copy(book = updatedBook) }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    private fun deleteBook() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val book = _uiState.value.book
                if (book != null) {
                    book.pdfUrl?.let { url ->
                        val fileName = url.substringAfterLast("/")
                        bookRepository.deleteFile("book_pdfs", fileName)
                    }
                    bookRepository.deleteBook(book.isbn)
                }
                _uiState.update { it.copy(isLoading = false, isDeleted = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }
}
