package com.shreyash16b.bookpedia.book.presentation.book_list

import com.shreyash16b.bookpedia.book.domain.Book

sealed interface BookListAction {
    data class onSearchQueryChange(val query : String) : BookListAction
    data class onBookCLick(val book : Book) : BookListAction
    data class onTabSelected(val newIndex : Int) : BookListAction
}