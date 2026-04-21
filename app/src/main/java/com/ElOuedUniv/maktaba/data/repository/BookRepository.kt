package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    
    fun getAllBooks(): Flow<List<Book>>
    
    fun getBookByIsbn(isbn: String): Book?

    fun addBook(book: Book)

    fun updateBook(book: Book)

    fun deleteBook(isbn: String)
}
