package com.estebanposada.artischallenge.ui.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estebanposada.artischallenge.R
import com.estebanposada.artischallenge.ui.detail.ArtistDetailState
import com.estebanposada.artischallenge.ui.theme.ArtisChallengeTheme
import com.estebanposada.domain.model.Artist

@Composable
fun ArtistDetailContent(
    modifier: Modifier = Modifier,
    state: ArtistDetailState,
    onViewAlbums: () -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        state.artist?.let { artist ->
            item { ArtistHeader(artist = artist) }
        }

        if (!state.isLoading) {
            item {
                Button(
                    onClick = onViewAlbums,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
                    Text(
                        stringResource(R.string.view_albums),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        state.artist?.members?.let { members ->
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(8.dp),
                ) {
                    Text(
                        stringResource(R.string.band_members),
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    FlowRow(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        members.forEach { artist ->
                            Chip(name = artist)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ArtistDetailContentPreview() {
    val artist = Artist(
        id = "id",
        name = "name",
        profile = "profile",
        members = listOf("Member 1", "Member 2")
    )
    val state = ArtistDetailState(artist = artist)
    ArtisChallengeTheme {
        ArtistDetailContent(state = state) {}
    }
}
