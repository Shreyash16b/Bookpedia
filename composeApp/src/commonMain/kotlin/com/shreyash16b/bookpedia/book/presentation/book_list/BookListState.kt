package com.shreyash16b.bookpedia.book.presentation.book_list

import com.shreyash16b.bookpedia.book.domain.Book
import com.shreyash16b.bookpedia.core.presentation.UiText

data class BookListState(
    val searchQuery : String = "Kotlin",
    val searchResults : List<Book> = emptyList(),
    val favouriteBooks : List<Book> = emptyList(),
    val ratedBooks : List<Book> = emptyList(),
    val isLoading : Boolean = false,
    val selectedTabIndex : Int = 0,
    val errorMessage : UiText? = null
)
