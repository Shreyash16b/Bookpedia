package com.shreyash16b.bookpedia.book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bookpedia.composeapp.generated.resources.Res
import bookpedia.composeapp.generated.resources.favourites
import bookpedia.composeapp.generated.resources.no_favourite_books
import bookpedia.composeapp.generated.resources.no_search_result
import bookpedia.composeapp.generated.resources.search_results
import com.shreyash16b.bookpedia.book.domain.Book
import com.shreyash16b.bookpedia.book.presentation.book_list.components.BookList
import com.shreyash16b.bookpedia.book.presentation.book_list.components.BookSearchBar
import com.shreyash16b.bookpedia.core.presentation.DarkBlue
import com.shreyash16b.bookpedia.core.presentation.DesertWhite
import com.shreyash16b.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
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
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun BookListScreen(
    onAction : (BookListAction) -> Unit,
    state: BookListState
){

    val keyboardController = LocalSoftwareKeyboardController.current

    val pagerState = rememberPagerState { 3 }

    val searchResultListState = rememberLazyListState()
    LaunchedEffect(state.searchResults){
        searchResultListState.animateScrollToItem(0) //Initial Loading to the first book
    }

    val favouritesListState = rememberLazyListState()

    LaunchedEffect(state.selectedTabIndex){
        pagerState.animateScrollToPage(state.selectedTabIndex) //Change the tab when selected
    }

    LaunchedEffect(pagerState.currentPage){
        onAction(BookListAction.onTabSelected(pagerState.currentPage))
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DarkBlue)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        BookSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = {
                onAction(BookListAction.onSearchQueryChange(it))
            },
            onImeSearchAction = {
                keyboardController?.hide()
            },
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            color = DesertWhite,
            shape = RoundedCornerShape(
                topStart = 24.dp,
                topEnd = 24.dp
            )
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TabRow(
                    state.selectedTabIndex,
                    modifier = Modifier
                        .padding(12.dp)
                        .widthIn(max = 700.dp),
                    contentColor = SandYellow,
                    containerColor = DesertWhite,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            color = SandYellow,
                            modifier = Modifier.tabIndicatorOffset(tabPositions[state.selectedTabIndex])
                        )
                    }
                ){
                    Tab(
                        selected = state.selectedTabIndex == 0,
                        onClick = {
                            onAction(BookListAction.onTabSelected(0))
                        },
                        modifier = Modifier
                            .weight(1f),
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.6f)
                    ){
                        Text(
                            text = stringResource(Res.string.search_results),
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )
                    }

                    Tab(
                        selected = state.selectedTabIndex == 1,
                        onClick = {
                            onAction(BookListAction.onTabSelected(1))
                        },
                        modifier = Modifier
                            .weight(1f),
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.6f)
                    ){
                        Text(
                            text = stringResource(Res.string.favourites),
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )
                    }
                }
                Spacer(
                    modifier = Modifier.height(4.dp)
                )
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ){ pageIndex ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        when (pageIndex) {
                            0 -> {
                                if (state.isLoading) {
                                    CircularProgressIndicator()
                                } else{
                                    when{
                                        state.errorMessage != null -> {
                                            Text(
                                                text = state.errorMessage.asString(),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }
                                        state.searchResults.isEmpty() -> {
                                            Text(
                                                text = stringResource(Res.string.no_search_result),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }
                                        else -> {
                                            BookList(
                                                books = state.searchResults,
                                                onBookClick = {
                                                    onAction(BookListAction.onBookCLick(it))
                                                },
                                                modifier = Modifier.fillMaxSize(),
                                                scrollState =searchResultListState
                                            )
                                        }
                                    }
                                }
                            }

                            1 -> {
                                if(state.favouriteBooks.isEmpty()){
                                    Text(
                                        text = stringResource(Res.string.no_favourite_books),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.headlineSmall
                                    )
                                } else{
                                    BookList(
                                        books = state.favouriteBooks,
                                        onBookClick = {
                                            onAction(BookListAction.onBookCLick(it))
                                        },
                                        modifier = Modifier.fillMaxSize(),
                                        scrollState = favouritesListState
                                    )
                                }
                            }
                            2 -> {}
                        }
                    }

                }
            }
        }
    }
}