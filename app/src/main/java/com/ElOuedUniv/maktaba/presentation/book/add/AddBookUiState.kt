package com.ElOuedUniv.maktaba.presentation.book.add

import android.net.Uri
import com.ElOuedUniv.maktaba.data.model.Category

data class AddBookUiState(
    val title: String = "",
    val isbn: String = "",
    val nbPages: String = "",
    val description: String = "",
    val categoryId: String? = null,
    val categories: List<Category> = emptyList(),
    val selectedPdfUri: Uri? = null,
    val selectedPdfName: String? = null,
    val titleError: String? = null,
    val isbnError: String? = null,
    val pagesError: String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isEditMode: Boolean = false,
    val errorMessage: String? = null,
    val selectedImageUri: Uri? = null,
    val existingImageUrl: String? = null,
    val existingPdfUrl: String? = null,
    val isFavorite: Boolean = false
)
