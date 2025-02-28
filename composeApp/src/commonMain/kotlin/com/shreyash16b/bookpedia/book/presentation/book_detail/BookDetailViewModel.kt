package com.shreyash16b.bookpedia.book.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.shreyash16b.bookpedia.app.Route
import com.shreyash16b.bookpedia.book.domain.BookRepository
import com.shreyash16b.bookpedia.core.domain.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val bookRepository: BookRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    val bookId = savedStateHandle.toRoute<Route.BookDetail>().id
    private val _state = MutableStateFlow(BookDetailState())
    val state = _state.
            onStart {
                fetchBookDescription()
                observeFavouriteStatus()
            }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: BookDetailAction){
        when(action) {
            is BookDetailAction.onSelectedBookChange -> {
                _state.update {
                    it.copy(book = action.book)
                }
            }
            BookDetailAction.onFavouriteClick -> {
                viewModelScope.launch {
                    if(state.value.isFavourite){
                        bookRepository.removeFromFavourites(id = bookId)
                    }   else{
                        state.value.book?.let{book ->
                            bookRepository.markAsFavourite(book)
                        }
                    }
                }
            }
            else -> Unit
        }
    }

    private fun observeFavouriteStatus(){
        bookRepository
            .isBookFavourite(id = bookId)
            .onEach { isFavourite ->
                _state.update {
                    it.copy(isFavourite = isFavourite)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchBookDescription()  {
        viewModelScope.launch {
            bookRepository.getBookDescription(bookId)
                .onSuccess {description ->
                    _state.update {
                        it.copy(
                            book = it.book
                                ?.copy(description = description),
                            isLoading = false
                            )
                    }
                }
        }
    }
}