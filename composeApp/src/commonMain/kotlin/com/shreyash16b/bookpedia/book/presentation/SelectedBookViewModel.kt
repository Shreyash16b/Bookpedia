package com.shreyash16b.bookpedia.book.presentation

import androidx.lifecycle.ViewModel
import com.shreyash16b.bookpedia.book.domain.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedBookViewModel : ViewModel() {

    private val _selectedBook = MutableStateFlow<Book?>(null)
    val selectedBook = _selectedBook.asStateFlow()

    fun onSelect(book : Book?){
        _selectedBook.value = book
    }
}