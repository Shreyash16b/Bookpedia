package com.shreyash16b.bookpedia

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import bookpedia.composeapp.generated.resources.Res
import bookpedia.composeapp.generated.resources.compose_multiplatform
import com.shreyash16b.bookpedia.book.data.network.KtorRemoteBookDataSource
import com.shreyash16b.bookpedia.book.data.repository.DefaultBookRepository
import com.shreyash16b.bookpedia.book.domain.Book
import com.shreyash16b.bookpedia.book.presentation.book_list.BookListScreenRoot
import com.shreyash16b.bookpedia.book.presentation.book_list.BookListViewModel
import com.shreyash16b.bookpedia.core.data.HttpClientFactory
import io.ktor.client.engine.HttpClientEngine

//MVI       Presentation -> Domain <- Data
@Composable
@Preview
fun App(engine : HttpClientEngine) {
    BookListScreenRoot(
        viewModel = remember { BookListViewModel(
            bookRepository = DefaultBookRepository(
                remoteBookDataSource = KtorRemoteBookDataSource(
                    httpClient = HttpClientFactory.create(engine)
                )
            )
        ) },
        onBookClick = {

        }
    )
}