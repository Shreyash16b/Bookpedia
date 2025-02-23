package com.shreyash16b.bookpedia.book.presentation.book_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shreyash16b.bookpedia.book.domain.Book
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookListScreenRoot(
    viewModel: BookListViewModel = koinViewModel(),
    onBookClick : (Book) -> Unit,
    modifier: Modifier = Modifier
){

    val state by viewModel.state.collectAsStateWithLifecycle()
    BookListScreen(
        state = state,
        onAction = { action ->
            when(action){
                is BookListAction.onBookCLick -> onBookClick(action.book)
                else -> Unit
            }

        }
    )
}

@Composable
private fun BookListScreen(
    onAction : (BookListAction) -> Unit,
    state: BookListState
){

}