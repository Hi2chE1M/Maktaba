package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book

class BookRepositoryImpl : BookRepository {

    private val booksList = listOf(
        Book(
            isbn = "978-0-13-235088-4",
            title = "Clean Code",
            nbPages = 464
        ),
        Book(
            isbn = "978-0-201-61622-4",
            title = "The Pragmatic Programmer",
            nbPages = 352
        ),
        Book(
            isbn = "978-0-262-03384-8",
            title = "Introduction to Algorithms",
            nbPages = 1312
        ),
        Book(
            isbn = "978-0-13-110362-7",
            title = "The C Programming Language",
            nbPages = 272
        ),
        Book(
            isbn = "978-0-596-52068-7",
            title = "Head First Design Patterns",
            nbPages = 694
        ),
        Book(
            isbn = "978-1-491-94721-2",
            title = "Kotlin in Action",
            nbPages = 360
        ),
        Book(
            isbn = "978-0-321-94732-3",
            title = "Effective Java",
            nbPages = 416
        ),
        Book(
            isbn = "978-1-59327-950-9",
            title = "Automate the Boring Stuff with Python",
            nbPages = 592
        ),
        Book(
            isbn = "978-0-13-468599-1",
            title = "Refactoring: Improving the Design of Existing Code",
            nbPages = 448
        ),
        Book(
            isbn = "978-1-449-37043-1",
            title = "Android Programming: The Big Nerd Ranch Guide",
            nbPages = 592
        )
    )

    override fun getAllBooks(): List<Book> {
        return booksList
    }

    override fun getBookByIsbn(isbn: String): Book? {
        return booksList.find { it.isbn == isbn }
    }
}
