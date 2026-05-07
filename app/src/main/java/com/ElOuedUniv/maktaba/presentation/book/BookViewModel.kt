package com.ElOuedUniv.maktaba.presentation.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.data.repository.BookRepository
import com.ElOuedUniv.maktaba.domain.usecase.AddBookUseCase
import com.ElOuedUniv.maktaba.domain.usecase.GetBooksUseCase
import com.ElOuedUniv.maktaba.domain.usecase.GetCategoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addBookUseCase: AddBookUseCase,
    private val bookRepository: BookRepository // Added to handle upload directly for simplicity
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookUiState())
    val uiState: StateFlow<BookUiState> = _uiState.asStateFlow()

    init {
        loadBooks()
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            getCategoriesUseCase()
                .catch { e ->
                    _uiState.update { it.copy(errorMessage = e.message) }
                }
                .collect { categoryList ->
                    _uiState.update { it.copy(categories = categoryList) }
                }
        }
    }

    fun loadBooks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getBooksUseCase()
                .catch { e ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
                }
                .collect { bookList ->
                    _uiState.update { it.copy(isLoading = false, books = bookList) }
                }
        }
    }

    fun onAction(action: BookUiAction) {
        when (action) {
            BookUiAction.RefreshBooks -> refreshBooks()
            BookUiAction.OnAddBookClick -> {
                _uiState.update { it.copy(isAddingBook = true) }
            }
            BookUiAction.OnDismissAddBook -> {
                _uiState.update { it.copy(isAddingBook = false) }
            }
            is BookUiAction.OnAddBookConfirm -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(isLoading = true) }
                    try {
                        var pdfUrl: String? = null
                        
                        // 1. Upload PDF if exists
                        if (action.pdfByteArray != null && action.pdfFileName != null) {
                            pdfUrl = bookRepository.uploadFile(
                                bucketName = "book_pdfs",
                                fileName = action.pdfFileName,
                                byteArray = action.pdfByteArray
                            )
                        }

                        // 2. Add Book to Database
                        val newBook = Book(
                            isbn = action.isbn,
                            title = action.title,
                            nbPages = action.nbPages,
                            categoryId = action.categoryId,
                            pdfUrl = pdfUrl
                        )
                        addBookUseCase(newBook)
                        
                        _uiState.update { it.copy(isAddingBook = false, isLoading = false) }
                        refreshBooks()
                    } catch (e: Exception) {
                        _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
                    }
                }
            }
        }
    }

    fun refreshBooks() {
        loadBooks()
    }
}
