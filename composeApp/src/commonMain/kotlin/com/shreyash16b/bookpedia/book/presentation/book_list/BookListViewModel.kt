@file:OptIn(FlowPreview::class)

package com.shreyash16b.bookpedia.book.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shreyash16b.bookpedia.book.domain.Book
import com.shreyash16b.bookpedia.book.domain.BookRepository
import com.shreyash16b.bookpedia.core.domain.onError
import com.shreyash16b.bookpedia.core.domain.onSuccess
import com.shreyash16b.bookpedia.core.presentation.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookListViewModel(
    private val bookRepository : BookRepository
) : ViewModel() {

    private var cachedBooks = emptyList<Book>()
    private var searchJob: Job? = null
    private var observeFavouriteJob: Job? = null

    private val _state = MutableStateFlow(BookListState())
    val state = _state
        .onStart {
            if(cachedBooks.isEmpty()){
                observeSearchQuery()
            }
            observerFavouriteBooks()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: BookListAction){
        when(action){
            is BookListAction.onBookCLick -> {

            }
            is BookListAction.onSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }
            is BookListAction.onTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.newIndex)
                }
            }
        }
    }

    private fun observeSearchQuery(){
        state
            .map{it.searchQuery}
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when{
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResults = cachedBooks
                            )
                        }
                    }
                    query.length>=2 ->{
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observerFavouriteBooks(){
        observeFavouriteJob?.cancel()
        observeFavouriteJob = bookRepository
            .getFavouriteBooks()
            .onEach { favouriteBooks ->
                _state.update {
                    it.copy(favouriteBooks = favouriteBooks)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchBooks(query : String) = viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            bookRepository
                .searchBooks(query)
                .onSuccess { searchResults->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            searchResults = searchResults
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            searchResults = emptyList(),
                            errorMessage = error.toUiText()
                        )
                    }
                }
        }
}