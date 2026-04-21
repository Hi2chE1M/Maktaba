package com.ElOuedUniv.maktaba.presentation.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.domain.usecase.AddBookUseCase
import com.ElOuedUniv.maktaba.domain.usecase.GetBooksUseCase
import com.ElOuedUniv.maktaba.domain.usecase.GetCategoriesUseCase
import com.ElOuedUniv.maktaba.data.repository.BookRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookUiState())
    val uiState: StateFlow<BookUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            // Load Categories
            val categories = getCategoriesUseCase().first()
            _uiState.update { it.copy(categories = categories) }

            // Load Books and apply filtering
            getBooksUseCase()
                .catch { e ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
                }
                .collect { bookList ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            books = bookList,
                            filteredBooks = filterBooks(bookList, state.searchQuery, state.selectedCategoryId)
                        )
                    }
                }
        }
    }

    fun onAction(action: BookUiAction) {
        when (action) {
            BookUiAction.RefreshBooks -> loadData()
            is BookUiAction.OnSearchQueryChange -> {
                _uiState.update { state ->
                    state.copy(
                        searchQuery = action.query,
                        filteredBooks = filterBooks(state.books, action.query, state.selectedCategoryId)
                    )
                }
            }
            is BookUiAction.OnCategoryFilterSelect -> {
                _uiState.update { state ->
                    state.copy(
                        selectedCategoryId = action.categoryId,
                        filteredBooks = filterBooks(state.books, state.searchQuery, action.categoryId)
                    )
                }
            }
            is BookUiAction.OnDeleteBook -> {
                viewModelScope.launch {
                    bookRepository.deleteBook(action.isbn)
                }
            }
            is BookUiAction.OnUpdateBook -> {
                viewModelScope.launch {
                    bookRepository.updateBook(action.book)
                }
            }
        }
    }

    private fun filterBooks(books: List<Book>, query: String, categoryId: String?): List<Book> {
        return books.filter { book ->
            val matchesQuery = book.title.contains(query, ignoreCase = true) || 
                               book.isbn.contains(query, ignoreCase = true)
            val matchesCategory = categoryId == null || book.categoryId == categoryId
            matchesQuery && matchesCategory
        }
    }
}
