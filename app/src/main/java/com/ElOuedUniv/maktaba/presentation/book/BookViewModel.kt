package com.ElOuedUniv.maktaba.presentation.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ElOuedUniv.maktaba.domain.usecase.GetBooksUseCase
import com.ElOuedUniv.maktaba.domain.usecase.AddBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase,
    private val addBookUseCase: AddBookUseCase
) : ViewModel() {

    // UI state
    private val _uiState = MutableStateFlow(BookUiState())
    val uiState: StateFlow<BookUiState> = _uiState.asStateFlow()

    // One-time events
    private val _uiEvent = MutableSharedFlow<BookUiEvent>()
    val uiEvent: SharedFlow<BookUiEvent> = _uiEvent

    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            getBooksUseCase()
                .catch { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message
                        )
                    }
                    _uiEvent.emit(BookUiEvent.ShowSnackbar("Error loading books"))
                }
                .collect { bookList ->
                    _uiState.update {
                        it.copy(
                            books = bookList,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onAction(action: BookUiAction) {
        when (action) {

            BookUiAction.RefreshBooks -> loadBooks()

            BookUiAction.OnAddBookClick -> {
                _uiState.update {
                    it.copy(isAddingBook = true)
                }
            }

            BookUiAction.OnDismissAddBook -> {
                _uiState.update {
                    it.copy(isAddingBook = false)
                }
            }

            is BookUiAction.OnAddBookConfirm -> {
                viewModelScope.launch {

                    // Add the book using UseCase
                    addBookUseCase(
                        com.ElOuedUniv.maktaba.data.model.Book(
                            title = action.title,
                            isbn = action.isbn,
                            nbPages = action.nbPages
                        )
                    )

                    // Hide the add dialog
                    _uiState.update { it.copy(isAddingBook = false) }

                    // Refresh book list
                    loadBooks()

                    // Show confirmation message
                    _uiEvent.emit(BookUiEvent.ShowSnackbar("Book added successfully"))
                }
            }
        }
    }
}