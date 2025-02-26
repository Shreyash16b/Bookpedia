package com.shreyash16b.bookpedia.book.presentation.book_detail

import com.shreyash16b.bookpedia.book.domain.Book

sealed interface BookDetailAction {
    data object onBackClick : BookDetailAction
    data object onFavouriteClick : BookDetailAction
    data class onSelectedBookChange(val book : Book) : BookDetailAction
}