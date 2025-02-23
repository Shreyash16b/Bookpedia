package com.shreyash16b.bookpedia

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shreyash16b.bookpedia.book.presentation.book_list.components.BookSearchBar

@Preview
@Composable
private fun BookSearchBarPreview(){
    MaterialTheme{
        BookSearchBar(
            searchQuery = "",
            modifier = Modifier.fillMaxWidth(),
            onImeSearchAction = {},
            onSearchQueryChange = {}
        )
    }
}