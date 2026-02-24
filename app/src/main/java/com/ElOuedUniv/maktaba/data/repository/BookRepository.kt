package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book

/**
 * Repository for managing book data
 * This follows the Repository pattern to abstract data sources
 */
class BookRepository {

    /**
     * TODO for Students (TP1 - Exercise 1):
     * Complete the book information for each book in the list below.
     * Add the following information for each book:
     * - isbn: Use a valid ISBN-13 format (e.g., "978-3-16-148410-0")
     * - nbPages: Add the actual number of pages
     *
     * Example:
     * Book(
     *     isbn = "978-0-13-468599-1",
     *     title = "Clean Code",
     *     nbPages = 464
     * )
     */
    private val booksList = listOf(
        Book(isbn = "978-0-13-539857-9", title = "Clean Code", nbPages = 672),
        Book(isbn = "978-0-13-211917-7", title = "The Pragmatic Programmer", nbPages = 352),
        Book(isbn = "978-0-20-142294-8", title = "Design Patterns", nbPages = 268),
        Book(isbn = "978-0-13-306526-8", title = "Refactoring", nbPages = 464),
        Book(isbn = "978-0-59-600712-6", title = "Head First Design Patterns", nbPages = 638),

        Book(isbn = "978-1-11-884341-3", title = "Professional Java EE Design Patterns", nbPages = 264),
        Book(isbn = "978-9-81-499825-3", title = "Handbook of Mobile Application Development", nbPages = 115),
        Book(isbn = "978-1-48-421314-8", title = "Beginning Hybrid Mobile Application Development", nbPages = 222),
        Book(isbn = "978-9-36-972509-0", title = "Database Management System", nbPages = 424),
        Book(isbn = "978-9-38-115931-6", title = "Introduction to Database Management System", nbPages = 288)
    )

    /**
     * TODO for Students (TP1 - Exercise 2):
     * Add 5 more books to the list above.
     * Choose books related to Computer Science, Programming, or any topic you like.
     * Remember to include complete information (ISBN, title, nbPages).
     *
     * Tip: You can find ISBN numbers for books on:
     * - Google Books
     * - Amazon
     * - GoodReads
     */

    /**
     * Get all books from the repository
     * @return List of all books
     */
    fun getAllBooks(): List<Book> {
        return booksList
    }

    /**
     * Get a book by ISBN
     * @param isbn The ISBN of the book to find
     * @return The book if found, null otherwise
     */
    fun getBookByIsbn(isbn: String): Book? {
        return booksList.find { it.isbn == isbn }
    }
}
