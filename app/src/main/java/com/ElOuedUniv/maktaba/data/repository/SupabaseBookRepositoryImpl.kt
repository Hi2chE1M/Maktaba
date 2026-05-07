package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SupabaseBookRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : BookRepository {

    override fun getAllBooks(): Flow<List<Book>> = flow {
        val books = supabaseClient.postgrest["books"]
            .select()
            .decodeList<Book>()
        emit(books)
    }

    override suspend fun getBookByIsbn(isbn: String): Book? {
        return supabaseClient.postgrest["books"]
            .select {
                filter {
                    eq("isbn", isbn)
                }
            }
            .decodeSingleOrNull<Book>()
    }

    override suspend fun addBook(book: Book) {
        supabaseClient.postgrest["books"].insert(book)
    }

    override suspend fun updateBook(book: Book) {
        supabaseClient.postgrest["books"].update(book) {
            filter {
                eq("isbn", book.isbn)
            }
        }
    }

    override suspend fun deleteBook(isbn: String) {
        supabaseClient.postgrest["books"].delete {
            filter {
                eq("isbn", isbn)
            }
        }
    }

    override suspend fun uploadFile(bucketName: String, fileName: String, byteArray: ByteArray): String {
        val bucket = supabaseClient.storage[bucketName]
        bucket.upload(fileName, byteArray) {
            upsert = true
        }
        return bucket.publicUrl(fileName)
    }

    override suspend fun deleteFile(bucketName: String, fileName: String) {
        supabaseClient.storage[bucketName].delete(fileName)
    }

    override suspend fun toggleFavorite(isbn: String) {
        val book = getBookByIsbn(isbn)
        book?.let {
            val updatedBook = it.copy(isFavorite = !it.isFavorite)
            updateBook(updatedBook)
        }
    }
}
