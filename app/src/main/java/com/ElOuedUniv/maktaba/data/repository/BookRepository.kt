package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun getAllBooks(): Flow<List<Book>>
    suspend fun getBookByIsbn(isbn: String): Book?
    suspend fun addBook(book: Book)
    suspend fun updateBook(book: Book)
    suspend fun deleteBook(isbn: String)
    suspend fun uploadFile(bucketName: String, fileName: String, byteArray: ByteArray): String
    suspend fun deleteFile(bucketName: String, fileName: String)
    suspend fun toggleFavorite(isbn: String)
}
