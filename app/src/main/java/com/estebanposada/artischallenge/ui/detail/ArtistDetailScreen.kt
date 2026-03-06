package com.estebanposada.artischallenge.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.estebanposada.artischallenge.R
import com.estebanposada.artischallenge.ui.common.ErrorState
import com.estebanposada.artischallenge.ui.detail.components.ArtistDetailContent
import com.estebanposada.artischallenge.ui.theme.ArtisChallengeTheme
import com.estebanposada.domain.model.Artist

@Composable
fun ArtistDetailScreen(
    viewModel: ArtistDetailViewModel = hiltViewModel(),
    onViewAlbums: () -> Unit,
    onBack: () -> Unit
) {
    val state = viewModel.state.value
    ArtistDetail(state, onBack = onBack, onViewAlbums = onViewAlbums)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArtistDetail(
    state: ArtistDetailState,
    onBack: () -> Unit,
    onViewAlbums: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.detail)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { innerPadding ->
        ArtistDetailContent(modifier = Modifier.padding(innerPadding), state) { onViewAlbums() }
        if (state.isLoading)
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        state.error?.let { ErrorState(error = it) }
    }
}

@Preview
@Composable
private fun ArtistDetailPreview() {
    val artist = Artist(
        id = "id",
        name = "name",
        profile = "profile",
        members = listOf("Member 1", "Member 2")
    )
    val state = ArtistDetailState(artist = artist)
    ArtisChallengeTheme {
        ArtistDetail(state, {}, {})
    }
}

@Preview
@Composable
private fun ArtistDetailLoadingPreview() {
    val state = ArtistDetailState()
    ArtisChallengeTheme {
        ArtistDetail(state, {}, {})
    }
}

@Preview
@Composable
private fun ArtistDetailErrorPreview() {
    val state = ArtistDetailState()
    ArtisChallengeTheme {
        ArtistDetail(state, {}, {})
    }
}
