package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor() : BookRepository {


    private val internalList = mutableListOf(
        Book(isbn = "11111", title = "Clean Code", nbPages = 10),
        Book(isbn = "22222", title = "The Pragmatic Programmer", nbPages = 123),
        Book(isbn = "33333", title = "Design Patterns", nbPages = 244),
        Book(isbn = "44444", title = "Refactoring", nbPages = 288),
        Book(isbn = "55555", title = "Head First Design Patterns", nbPages = 300)
    )


    private val booksFlow = MutableSharedFlow<List<Book>>(replay = 1).apply {
        tryEmit(internalList)
    }


    override fun getAllBooks(): Flow<List<Book>> = flow {
        delay(2000) 
        emitAll(booksFlow)
    }


    override fun getBookByIsbn(isbn: String): Book? {
        return internalList.find { it.isbn == isbn }
    }

    override fun addBook(book: Book) {
        internalList.add(book)

        booksFlow.tryEmit(internalList.toList())
    }
}