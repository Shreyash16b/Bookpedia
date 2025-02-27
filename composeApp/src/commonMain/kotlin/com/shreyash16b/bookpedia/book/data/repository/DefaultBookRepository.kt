package com.shreyash16b.bookpedia.book.data.repository

import com.shreyash16b.bookpedia.book.data.mappers.toBook
import com.shreyash16b.bookpedia.book.data.network.KtorRemoteBookDataSource
import com.shreyash16b.bookpedia.book.domain.Book
import com.shreyash16b.bookpedia.book.domain.BookRepository
import com.shreyash16b.bookpedia.core.domain.DataError
import com.shreyash16b.bookpedia.core.domain.Result
import com.shreyash16b.bookpedia.core.domain.map

class DefaultBookRepository(
    private val remoteBookDataSource: KtorRemoteBookDataSource
) : BookRepository{
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query)
            .map { dto ->
                dto.results.map { it.toBook() }
            }
    }

    override suspend fun getBookDescription(bookId: String): Result<String?, DataError.Remote> {
        return remoteBookDataSource
            .getBookDescription(bookId)
            .map { it.description }
    }
}