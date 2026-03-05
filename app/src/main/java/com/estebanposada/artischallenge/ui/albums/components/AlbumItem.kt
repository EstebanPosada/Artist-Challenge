package com.estebanposada.artischallenge.ui.albums.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estebanposada.domain.model.Album

@Composable
fun AlbumItem(modifier: Modifier = Modifier, album: Album) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(4.dp)
    ) {
        Text(text = album.title, style = MaterialTheme.typography.titleMedium)
        album.year?.let { Text(text = "Year: $it", style = MaterialTheme.typography.bodySmall) }
        if (album.genres.isNotEmpty()) Text(
            text = "Genres: ${album.genres.joinToString(",")}",
            style = MaterialTheme.typography.bodySmall
        )
        if (album.labels.isNotEmpty()) Text(
            text = "Labels: ${album.labels.joinToString("/")}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview
@Composable
private fun AlbumItemPreview() {
    val album = Album(
        id = "123",
        title = "Title",
        year = 123,
        genres = listOf("Rock, Pop"),
        labels = listOf("label1", "label2")
    )
    AlbumItem(album = album)
}