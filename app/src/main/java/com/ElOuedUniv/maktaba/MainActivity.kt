package com.ElOuedUniv.maktaba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ElOuedUniv.maktaba.data.repository.BookRepositoryImpl
import com.ElOuedUniv.maktaba.data.repository.CategoryRepositoryImpl
import com.ElOuedUniv.maktaba.domain.usecase.GetBooksUseCase
import com.ElOuedUniv.maktaba.domain.usecase.GetCategoriesUseCase
import com.ElOuedUniv.maktaba.presentation.view.BookListView
import com.ElOuedUniv.maktaba.presentation.view.CategoryListView
import com.ElOuedUniv.maktaba.presentation.theme.MaktabaTheme
import com.ElOuedUniv.maktaba.presentation.viewmodel.BookViewModel
import com.ElOuedUniv.maktaba.presentation.viewmodel.CategoryViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Book dependencies
        val bookRepository = BookRepositoryImpl()
        val getBooksUseCase = GetBooksUseCase(bookRepository)
        val bookViewModel = BookViewModel(getBooksUseCase)

        // Category dependencies
        val categoryRepository = CategoryRepositoryImpl()
        val getCategoriesUseCase = GetCategoriesUseCase(categoryRepository)
        val categoryViewModel = CategoryViewModel(getCategoriesUseCase)

        setContent {
            MaktabaTheme {
                var showCategories by remember { mutableStateOf(false) }

                if (showCategories) {
                    CategoryListView(
                        viewModel = categoryViewModel,
                        onBackClick = { showCategories = false }
                    )
                } else {
                    BookListView(
                        viewModel = bookViewModel,
                        onCategoriesClick = { showCategories = true }
                    )
                }
            }
        }
    }
}
