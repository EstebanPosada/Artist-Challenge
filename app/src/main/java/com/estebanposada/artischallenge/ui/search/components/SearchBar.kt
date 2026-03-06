package com.estebanposada.artischallenge.ui.search.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.estebanposada.artischallenge.R
import com.estebanposada.artischallenge.ui.theme.ArtisChallengeTheme
import kotlinx.coroutines.flow.debounce

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit,
) {
    var q by remember { mutableStateOf("") }
    LaunchedEffect(q) {
        snapshotFlow { q }.debounce(500).collect { debounce ->
            if (debounce.isNotBlank()) {
                onQueryChange(debounce)
            }
        }
    }
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = q,
            onValueChange = { q = it },
            placeholder = { Text(stringResource(R.string.type_artist)) },
            singleLine = true,
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            )
        )
        IconButton(onClick = { q = "" }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.type_search)
            )
        }
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
    ArtisChallengeTheme {
        SearchBar(onQueryChange = {})
    }
}
