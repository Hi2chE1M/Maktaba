package com.ElOuedUniv.maktaba.presentation.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object BookList : Screen("book_list")
    object BookDetail : Screen("book_detail/{isbn}") {
        fun createRoute(isbn: String) = "book_detail/$isbn"
    }
    object CategoryList : Screen("category_list")
    object AddBook : Screen("add_book?isbn={isbn}") {
        fun createRoute(isbn: String? = null) = if (isbn != null) "add_book?isbn=$isbn" else "add_book"
    }
}
