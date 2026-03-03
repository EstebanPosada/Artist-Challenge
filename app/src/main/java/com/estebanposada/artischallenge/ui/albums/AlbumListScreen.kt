package com.estebanposada.artischallenge.ui.albums

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.estebanposada.artischallenge.ui.theme.ArtisChallengeTheme

@Composable
fun AlbumListScreen(viewModel: AlbumListViewModel = hiltViewModel()) {
    val state = viewModel.state.value
    AlbumList(state)
}

@Composable
private fun AlbumList(state: AlbumListState) {
    when (state) {
        is AlbumListState.Loading -> CircularProgressIndicator()
        is AlbumListState.Success -> {
            val artists = listOf("A", "b")
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(artists) {
                    Text(it)
                }
            }
        }

        is AlbumListState.Error -> Text(
            "Something went wrong",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Preview
@Composable
private fun AlbumListPreview() {
    val state = AlbumListState.Success("")
    ArtisChallengeTheme {
        AlbumList(state)
    }
}