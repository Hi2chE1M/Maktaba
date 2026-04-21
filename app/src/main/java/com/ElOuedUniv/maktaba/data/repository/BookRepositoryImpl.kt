package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor() : BookRepository {

    private val _booksList = mutableListOf(
        Book(isbn = "9780132350884", title = "Clean Code", nbPages = 464, imageUrl = "https://covers.openlibrary.org/b/isbn/9780132350884-L.jpg", categoryId = "1"),
        Book(isbn = "9780201616224", title = "The Pragmatic Programmer", nbPages = 352, imageUrl = "https://covers.openlibrary.org/b/isbn/9780201616224-L.jpg", categoryId = "1"),
        Book(isbn = "9780201633610", title = "Design Patterns", nbPages = 395, imageUrl = "https://covers.openlibrary.org/b/isbn/9780201633610-L.jpg", categoryId = "1"),
        Book(isbn = "9780201485677", title = "Refactoring", nbPages = 461, imageUrl = "https://covers.openlibrary.org/b/isbn/9780201485677-L.jpg", categoryId = "1"),
        Book(isbn = "9780596007126", title = "Head First Design Patterns", nbPages = 694, imageUrl = "https://covers.openlibrary.org/b/isbn/9780596007126-L.jpg", categoryId = "1"),
        Book(isbn = "9780134494166", title = "Clean Architecture", nbPages = 432, imageUrl = "https://covers.openlibrary.org/b/isbn/9780134494166-L.jpg", categoryId = "1"),
        Book(isbn = "9780134685991", title = "Effective Java", nbPages = 416, imageUrl = "https://covers.openlibrary.org/b/isbn/9780134685991-L.jpg", categoryId = "1"),
        Book(isbn = "9781617294532", title = "Kotlin in Action", nbPages = 360, imageUrl = "https://covers.openlibrary.org/b/isbn/9781617294532-L.jpg", categoryId = "1"),
        Book(isbn = "9781119299318", title = "Android Programming", nbPages = 552, imageUrl = "https://covers.openlibrary.org/b/isbn/9781119299318-L.jpg", categoryId = "4"),
        Book(isbn = "9780135957059", title = "The Clean Coder", nbPages = 256, imageUrl = "https://covers.openlibrary.org/b/isbn/9780135957059-L.jpg", categoryId = "1")
    )

    private val booksFlow = MutableSharedFlow<List<Book>>(replay = 1).apply {
        tryEmit(_booksList.toList())
    }
    
    override fun getAllBooks(): Flow<List<Book>> = flow {
        emitAll(booksFlow)
    }

    override fun getBookByIsbn(isbn: String): Book? {
        return _booksList.find { it.isbn == isbn }
    }

    override fun addBook(book: Book) {
        _booksList.add(book)
        booksFlow.tryEmit(_booksList.toList())
    }

    override fun updateBook(book: Book) {
        val index = _booksList.indexOfFirst { it.isbn == book.isbn }
        if (index != -1) {
            _booksList[index] = book
            booksFlow.tryEmit(_booksList.toList())
        }
    }

    override fun deleteBook(isbn: String) {
        _booksList.removeAll { it.isbn == isbn }
        booksFlow.tryEmit(_booksList.toList())
    }
}
