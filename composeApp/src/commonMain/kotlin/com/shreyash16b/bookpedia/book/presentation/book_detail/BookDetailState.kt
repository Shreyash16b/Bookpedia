package com.shreyash16b.bookpedia.book.presentation.book_detail

import com.shreyash16b.bookpedia.book.domain.Book

class BookDetailState(
    val isLoading :Boolean = true,
    val isFavourite :Boolean = false,
    val book : Book? = null
) {
}