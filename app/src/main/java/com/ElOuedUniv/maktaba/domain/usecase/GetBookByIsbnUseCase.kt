package com.ElOuedUniv.maktaba.domain.usecase

import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.data.repository.BookRepository

class GetBookByIsbnUseCase(
    private val bookRepository: BookRepository
) {
    suspend operator fun invoke(isbn: String): Book? {
        return bookRepository.getBookByIsbn(isbn)
    }
}
