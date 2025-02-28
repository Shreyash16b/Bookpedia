package com.shreyash16b.bookpedia.book.domain

import com.shreyash16b.bookpedia.core.domain.DataError
import com.shreyash16b.bookpedia.core.domain.EmptyResult
import com.shreyash16b.bookpedia.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookId : String): Result<String?, DataError.Remote>

    fun getFavouriteBooks() : Flow<List<Book>>
    fun isBookFavourite(id : String) : Flow<Boolean>
    suspend fun markAsFavourite(book : Book) : EmptyResult<DataError.Local>
    suspend fun removeFromFavourites(id : String)
}