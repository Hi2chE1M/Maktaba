package com.ElOuedUniv.maktaba.presentation.book.add

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.domain.usecase.AddBookUseCase
import com.ElOuedUniv.maktaba.domain.usecase.GetCategoriesUseCase
import com.ElOuedUniv.maktaba.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor(
    private val addBookUseCase: AddBookUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val bookRepository: BookRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AddBookUiState())
    val uiState = _uiState.asStateFlow()

    private val bookIsbn: String? = savedStateHandle["isbn"]

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val categories = getCategoriesUseCase().first()
            _uiState.update { it.copy(categories = categories) }
            
            bookIsbn?.let { isbn ->
                val book = bookRepository.getBookByIsbn(isbn)
                book?.let { b ->
                    _uiState.update { 
                        it.copy(
                            title = b.title,
                            isbn = b.isbn,
                            nbPages = b.nbPages.toString(),
                            imageUrl = b.imageUrl ?: "",
                            categoryId = b.categoryId,
                            isEditMode = true
                        )
                    }
                    validateInputs()
                }
            }
        }
    }

    fun onAction(action: AddBookUiAction) {
        when (action) {
            is AddBookUiAction.OnTitleChange -> {
                _uiState.update { it.copy(title = action.title) }
                validateInputs()
            }
            is AddBookUiAction.OnIsbnChange -> {
                // In edit mode, we might not want to allow ISBN change if it's the primary key
                if (!_uiState.value.isEditMode) {
                    _uiState.update { it.copy(isbn = action.isbn) }
                }
                validateInputs()
            }
            is AddBookUiAction.OnPagesChange -> {
                _uiState.update { it.copy(nbPages = action.pages) }
                validateInputs()
            }
            is AddBookUiAction.OnCategoryChange -> {
                _uiState.update { it.copy(categoryId = action.categoryId) }
                validateInputs()
            }
            is AddBookUiAction.OnImageUrlChange -> {
                _uiState.update { it.copy(imageUrl = action.url) }
                validateInputs()
            }
            AddBookUiAction.OnAddClick -> {
                if (_uiState.value.isFormValid) {
                    saveBook()
                }
            }
        }
    }

    private fun validateInputs() {
        val title = _uiState.value.title
        val isbn = _uiState.value.isbn
        val nbPages = _uiState.value.nbPages

        val titleError = if (title.isBlank()) "Title cannot be empty" else null
        val isbnError = if (isbn.length != 13 || isbn.any { !it.isDigit() }) "ISBN must be 13 digits" else null
        val pagesInt = nbPages.toIntOrNull()
        val pagesError = if (pagesInt == null || pagesInt <= 0) "Pages must be a positive number" else null

        _uiState.update { 
            it.copy(
                titleError = titleError,
                isbnError = isbnError,
                nbPagesError = pagesError,
                isFormValid = titleError == null && isbnError == null && pagesError == null && it.categoryId != null
            )
        }
    }

    private fun saveBook() {
        val currentState = _uiState.value
        val book = Book(
            isbn = currentState.isbn,
            title = currentState.title,
            nbPages = currentState.nbPages.toIntOrNull() ?: 0,
            imageUrl = currentState.imageUrl.ifBlank { null },
            categoryId = currentState.categoryId
        )
        
        viewModelScope.launch {
            if (currentState.isEditMode) {
                bookRepository.updateBook(book)
            } else {
                addBookUseCase(book)
            }
            _uiState.update { it.copy(isSuccess = true) }
        }
    }
}
