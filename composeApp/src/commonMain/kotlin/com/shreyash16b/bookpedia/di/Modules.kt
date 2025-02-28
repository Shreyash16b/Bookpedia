package com.shreyash16b.bookpedia.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.shreyash16b.bookpedia.book.data.database.DatabaseFactory
import com.shreyash16b.bookpedia.book.data.database.FavouriteBookDatabase
import com.shreyash16b.bookpedia.book.data.network.KtorRemoteBookDataSource
import com.shreyash16b.bookpedia.book.data.network.RemoteBookDataSource
import com.shreyash16b.bookpedia.book.data.repository.DefaultBookRepository
import com.shreyash16b.bookpedia.book.domain.BookRepository
import com.shreyash16b.bookpedia.book.presentation.SelectedBookViewModel
import com.shreyash16b.bookpedia.book.presentation.book_list.BookListViewModel
import com.shreyash16b.bookpedia.book.presentation.book_detail.BookDetailViewModel
import com.shreyash16b.bookpedia.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule : Module
val sharedModule = module{
    single {
        HttpClientFactory.create(get())
    }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(driver = BundledSQLiteDriver())
            .build()
    }

    single { get<FavouriteBookDatabase>().favouriteBookDao }

    viewModelOf(::BookListViewModel)
    viewModelOf(::SelectedBookViewModel)

    viewModelOf(::BookDetailViewModel)
}