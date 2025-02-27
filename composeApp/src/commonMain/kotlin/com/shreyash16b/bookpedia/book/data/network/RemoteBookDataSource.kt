package com.shreyash16b.bookpedia.book.data.network

import com.shreyash16b.bookpedia.book.data.dto.BookWorkDto
import com.shreyash16b.bookpedia.book.data.dto.SearchResponseDto
import com.shreyash16b.bookpedia.core.domain.DataError
import com.shreyash16b.bookpedia.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getBookDescription(bookWordId : String) : Result<BookWorkDto, DataError.Remote>
}