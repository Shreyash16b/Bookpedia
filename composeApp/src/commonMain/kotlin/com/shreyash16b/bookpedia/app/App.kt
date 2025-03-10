package com.shreyash16b.bookpedia.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.shreyash16b.bookpedia.book.presentation.SelectedBookViewModel
import com.shreyash16b.bookpedia.book.presentation.book_detail.BookDetailAction
import com.shreyash16b.bookpedia.book.presentation.book_detail.BookDetailScreenRoot
import com.shreyash16b.bookpedia.book.presentation.book_detail.BookDetailViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

import com.shreyash16b.bookpedia.book.presentation.book_list.BookListScreenRoot
import com.shreyash16b.bookpedia.book.presentation.book_list.BookListViewModel
import org.koin.compose.viewmodel.koinViewModel

//MVI       Presentation -> Domain <- Data
@Composable
@Preview
fun App() {
    MaterialTheme{
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.BookGraph
        ){
            navigation<Route.BookGraph>(
                startDestination = Route.BookList
            ){
                composable<Route.BookList> {
                    val viewModel = koinViewModel<BookListViewModel>()
                    val selectedBookViewModel =
                        it.sharedKoinViewModel<SelectedBookViewModel>(navController)

                    LaunchedEffect(true){
                        selectedBookViewModel.onSelect(null)
                    }

                    BookListScreenRoot(
                        viewModel = viewModel,
                        onBookClick = { book ->
                            selectedBookViewModel.onSelect(book)
                            navController.navigate(
                                Route.BookDetail(book.id)
                            )
                        }
                    )
                }
                composable<Route.BookDetail> { it ->
                    val selectedBookViewModel =
                        it.sharedKoinViewModel<SelectedBookViewModel>(navController)

                    val viewModel = koinViewModel<BookDetailViewModel>()
                    val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()

                    LaunchedEffect(selectedBook){
                        selectedBook?.let{
                            viewModel.onAction(BookDetailAction.onSelectedBookChange(it))
                        }
                    }
                    BookDetailScreenRoot(
                        viewModel = viewModel,
                        onBackClick = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private inline fun<reified T : ViewModel>NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
) : T{
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this){
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}
