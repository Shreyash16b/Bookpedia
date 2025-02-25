package com.shreyash16b.bookpedia.book.domain

import com.shreyash16b.bookpedia.core.domain.DataError
import com.shreyash16b.bookpedia.core.domain.Result
interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>

}