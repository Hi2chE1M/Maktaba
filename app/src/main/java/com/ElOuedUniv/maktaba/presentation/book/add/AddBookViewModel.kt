package com.ElOuedUniv.maktaba.presentation.book.add

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.domain.usecase.AddBookUseCase
import com.ElOuedUniv.maktaba.data.repository.BookRepository
import com.ElOuedUniv.maktaba.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.SupabaseClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.util.UUID

@HiltViewModel
class AddBookViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val addBookUseCase: AddBookUseCase,
    private val bookRepository: BookRepository,
    private val categoryRepository: CategoryRepository,
    private val supabaseClient: SupabaseClient,
    @param:ApplicationContext private val context: Context
) : ViewModel() {
    
    private val isbnArg: String? = savedStateHandle["isbn"]
    
    private val _uiState = MutableStateFlow(AddBookUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadCategories()
        if (isbnArg != null) {
            loadBookToEdit(isbnArg)
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            categoryRepository.getAllCategories().collect { categories ->
                _uiState.update { it.copy(categories = categories) }
            }
        }
    }

    private fun loadBookToEdit(isbn: String) {
        _uiState.update { it.copy(isLoading = true, isEditMode = true) }
        viewModelScope.launch {
            try {
                val book = bookRepository.getBookByIsbn(isbn)
                if (book != null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            title = book.title,
                            isbn = book.isbn,
                            nbPages = book.nbPages.toString(),
                            description = book.description ?: "",
                            categoryId = book.categoryId,
                            existingImageUrl = book.imageUrl,
                            existingPdfUrl = book.pdfUrl,
                            isFavorite = book.isFavorite
                        )
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false, errorMessage = "Book not found") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun onAction(action: AddBookUiAction) {
        when (action) {
            is AddBookUiAction.OnTitleChange -> _uiState.update { it.copy(title = action.title, titleError = null) }
            is AddBookUiAction.OnIsbnChange -> _uiState.update { it.copy(isbn = action.isbn, isbnError = null) }
            is AddBookUiAction.OnPagesChange -> _uiState.update { it.copy(nbPages = action.pages, pagesError = null) }
            is AddBookUiAction.OnDescriptionChange -> _uiState.update { it.copy(description = action.description) }
            is AddBookUiAction.OnCategoryChange -> _uiState.update { it.copy(categoryId = action.categoryId) }
            is AddBookUiAction.OnPdfSelected -> _uiState.update { it.copy(selectedPdfUri = action.uri, selectedPdfName = action.name) }
            is AddBookUiAction.OnFavoriteToggle -> _uiState.update { it.copy(isFavorite = action.isFavorite) }
            is AddBookUiAction.OnCoverChange -> _uiState.update { it.copy(selectedImageUri = action.uri) }
            AddBookUiAction.OnAddClick -> saveBook()
        }
    }

    private fun saveBook() {
        val currentState = _uiState.value
        if (!validateFields()) return

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                var imageUrl = currentState.existingImageUrl
                var pdfUrl = currentState.existingPdfUrl
                
                // Upload new image if selected
                currentState.selectedImageUri?.let { uri ->
                    val coverBytes = readFileBytes(uri)
                    val fileName = "covers/${currentState.isbn}_${UUID.randomUUID()}.jpg"
                    imageUrl = bookRepository.uploadFile("book_pdfs", fileName, coverBytes)
                }

                // Upload PDF if selected
                currentState.selectedPdfUri?.let { uri ->
                    val pdfBytes = readFileBytes(uri)
                    val fileName = "pdfs/${currentState.isbn}_${UUID.randomUUID()}.pdf"
                    pdfUrl = bookRepository.uploadFile("book_pdfs", fileName, pdfBytes)
                }

                val book = Book(
                    isbn = currentState.isbn.trim(),
                    title = currentState.title.trim(),
                    nbPages = currentState.nbPages.toIntOrNull() ?: 0,
                    description = currentState.description.trim(),
                    imageUrl = imageUrl,
                    pdfUrl = pdfUrl,
                    categoryId = currentState.categoryId,
                    isFavorite = currentState.isFavorite
                )
                
                if (currentState.isEditMode) {
                    bookRepository.updateBook(book)
                } else {
                    addBookUseCase(book)
                }
                
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.localizedMessage) }
            }
        }
    }

    private fun validateFields(): Boolean {
        val currentState = _uiState.value
        val titleError = if (currentState.title.isBlank()) "العنوان مطلوب" else null
        val isbnError = if (currentState.isbn.isBlank()) "ISBN مطلوب" else null
        
        _uiState.update { it.copy(titleError = titleError, isbnError = isbnError) }
        return titleError == null && isbnError == null
    }

    private fun readFileBytes(uri: Uri): ByteArray {
        return context.contentResolver.openInputStream(uri)?.use { it.readBytes() } 
            ?: error("Unable to read file")
    }
}
