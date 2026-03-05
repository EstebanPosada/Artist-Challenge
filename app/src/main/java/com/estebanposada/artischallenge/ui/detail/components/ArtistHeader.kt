package com.estebanposada.artischallenge.ui.detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.estebanposada.artischallenge.R
import com.estebanposada.domain.model.Artist

@Composable
fun ArtistHeader(modifier: Modifier = Modifier, artist: Artist) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(artist.thumbnail).crossfade(true)
                .placeholder(R.drawable.artist_placeholder)
                .error(R.drawable.artist_error).build()
        )
        Image(
            painter = painter,
            contentScale = ContentScale.Fit,
            contentDescription = "Artist: ${artist.id}",
            modifier = Modifier.clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            artist.name,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        artist.profile?.let { profile ->
            Text(
                profile,
                modifier = Modifier.padding(horizontal = 8.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Preview
@Composable
private fun ArtistHeaderPreview() {
    val artist = Artist(
        id = "123",
        name = "Artist",
        profile = "Profile"
    )
    ArtistHeader(artist = artist)
}
