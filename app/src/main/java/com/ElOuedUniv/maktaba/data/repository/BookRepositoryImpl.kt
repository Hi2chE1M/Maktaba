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
        Book(isbn = "11111", title = "Clean Code", nbPages = 10),
        Book(isbn = "22222", title = "The Pragmatic Programmer", nbPages = 0),
        Book(isbn = "33333", title = "Design Patterns", nbPages = 0),
        Book(isbn = "44444", title = "Refactoring", nbPages = 0),
        Book(isbn = "55555", title = "Head First Design Patterns", nbPages = 0)
    )

    private val booksFlow = MutableSharedFlow<List<Book>>(replay = 1).apply {
        tryEmit(_booksList.toList())
    }
    
    override fun getAllBooks(): Flow<List<Book>> = flow {
        delay(500) // Simulate delay
        emitAll(booksFlow)
    }

    override suspend fun getBookByIsbn(isbn: String): Book? {
        return _booksList.find { it.isbn == isbn }
    }

    override suspend fun addBook(book: Book) {
        _booksList.add(book)
        booksFlow.tryEmit(_booksList.toList())
    }

    override suspend fun updateBook(book: Book) {
        val index = _booksList.indexOfFirst { it.isbn == book.isbn }
        if (index != -1) {
            _booksList[index] = book
            booksFlow.tryEmit(_booksList.toList())
        }
    }

    override suspend fun deleteBook(isbn: String) {
        _booksList.removeAll { it.isbn == isbn }
        booksFlow.tryEmit(_booksList.toList())
    }

    override suspend fun uploadFile(bucketName: String, fileName: String, byteArray: ByteArray): String {
        return "https://example.com/$bucketName/$fileName"
    }

    override suspend fun deleteFile(bucketName: String, fileName: String) {
        // Mock delete
    }

    override suspend fun toggleFavorite(isbn: String) {
        val index = _booksList.indexOfFirst { it.isbn == isbn }
        if (index != -1) {
            val book = _booksList[index]
            _booksList[index] = book.copy(isFavorite = !book.isFavorite)
            booksFlow.tryEmit(_booksList.toList())
        }
    }
}
