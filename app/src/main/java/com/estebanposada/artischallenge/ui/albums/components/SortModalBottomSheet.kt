package com.estebanposada.artischallenge.ui.albums.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortModalBottomSheet(
    modifier: Modifier = Modifier,
    onSort: (AlbumSortType) -> Unit,
    selectedSort: AlbumSortType,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(
            modifier = modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text("Filter", style = MaterialTheme.typography.titleMedium)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FilterChip(
                    selected = selectedSort == AlbumSortType.YEAR,
                    onClick = { onSort(AlbumSortType.YEAR) },
                    label = { Text("Year") })
                FilterChip(
                    selected = selectedSort == AlbumSortType.GENRE,
                    onClick = { onSort(AlbumSortType.GENRE) },
                    label = { Text("Genre") })
                FilterChip(
                    selected = selectedSort == AlbumSortType.LABEL,
                    onClick = { onSort(AlbumSortType.LABEL) },
                    label = { Text("Label") })
            }
        }
    }
}

@Preview
@Composable
private fun SortModalBottomSheetPreview() {
    SortModalBottomSheet(onSort = {}, selectedSort = AlbumSortType.GENRE) {}
}