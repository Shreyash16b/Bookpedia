package com.shreyash16b.bookpedia.book.data.repository

import androidx.sqlite.SQLiteException
import com.shreyash16b.bookpedia.book.data.database.FavouriteBookDao
import com.shreyash16b.bookpedia.book.data.mappers.toBook
import com.shreyash16b.bookpedia.book.data.mappers.toBookEntity
import com.shreyash16b.bookpedia.book.data.network.RemoteBookDataSource
import com.shreyash16b.bookpedia.book.domain.Book
import com.shreyash16b.bookpedia.book.domain.BookRepository
import com.shreyash16b.bookpedia.core.domain.DataError
import com.shreyash16b.bookpedia.core.domain.EmptyResult
import com.shreyash16b.bookpedia.core.domain.Result
import com.shreyash16b.bookpedia.core.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favouriteBookDao: FavouriteBookDao
) : BookRepository{
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query)
            .map { dto ->
                dto.results.map { it.toBook() }
            }
    }

    override suspend fun getBookDescription(bookId: String): Result<String?, DataError.Remote> {
        val localResult = favouriteBookDao.getFavouriteBook(bookId)

        return if(localResult == null) {
            remoteBookDataSource
                .getBookDescription(bookId)
                .map { it.description }
        }   else{
            Result.Success(localResult.description)
        }
    }

    override fun getFavouriteBooks(): Flow<List<Book>> {
        return favouriteBookDao.getFavouriteBook().map { bookEntities ->
            bookEntities.map { it.toBook() }
        }
    }

    override fun isBookFavourite(id: String): Flow<Boolean> {
        return favouriteBookDao.getFavouriteBook().map { bookEntities ->
            bookEntities.any { it.id == id }
        }
    }

    override suspend fun markAsFavourite(book: Book): EmptyResult<DataError.Local> {
        return try {
            favouriteBookDao.upsert(book.toBookEntity())
            Result.Success(Unit)
        }   catch (e : SQLiteException){
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun removeFromFavourites(id: String) {
        return favouriteBookDao.deleteFavouriteBook(id = id)
    }
}