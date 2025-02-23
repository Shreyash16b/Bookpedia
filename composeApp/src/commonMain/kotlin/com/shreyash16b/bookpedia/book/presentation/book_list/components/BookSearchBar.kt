package com.shreyash16b.bookpedia.book.presentation.book_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import bookpedia.composeapp.generated.resources.Res
import bookpedia.composeapp.generated.resources.close_search
import bookpedia.composeapp.generated.resources.test
import bookpedia.composeapp.generated.resources.search_hint
import com.shreyash16b.bookpedia.core.presentation.DarkBlue
import com.shreyash16b.bookpedia.core.presentation.DesertWhite
import com.shreyash16b.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource

@Composable
fun BookSearchBar(
    searchQuery : String,
    onSearchQueryChange : (String) -> Unit,
    onImeSearchAction : () -> Unit,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        shape = RoundedCornerShape(75),
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = DarkBlue,
            focusedBorderColor = SandYellow,
            focusedTextColor = Color.Black
        ),
        placeholder = {
            Text(
                text = stringResource(Res.string.search_hint)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(Res.string.search_hint),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
                )
        },
        singleLine = true,
        keyboardActions = KeyboardActions(
            onSearch = {
                onImeSearchAction()
            }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        trailingIcon = {
            AnimatedVisibility(
                visible = searchQuery.isNotBlank()
            ){
                IconButton(
                    onClick = {
                        onSearchQueryChange("")
                    }
                ){
                    Icon(
                        Icons.Default.Close,
                        contentDescription = stringResource(Res.string.close_search),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        modifier = modifier
            .background(
                shape = RoundedCornerShape(85),
                color = DesertWhite
            )
            .minimumInteractiveComponentSize()
    )
}