package com.estebanposada.artischallenge.ui.albums

import androidx.lifecycle.SavedStateHandle
import com.estebanposada.artischallenge.ui.album
import com.estebanposada.artischallenge.ui.albums.components.AlbumSortType
import com.estebanposada.artischallenge.ui.artist
import com.estebanposada.artischallenge.ui.common.UiError
import com.estebanposada.artischallenge.ui.detail.ArtistDetailViewModel.Companion.ARTIST_ID
import com.estebanposada.domain.Error
import com.estebanposada.domain.Resource
import com.estebanposada.domain.model.Album
import com.estebanposada.domain.usecase.GetAlbumInfoUseCase
import com.estebanposada.domain.usecase.SearchAlbumUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AlbumListViewModelTest {
    private lateinit var viewModel: AlbumListViewModel
    private lateinit var searchAlbumUseCase: SearchAlbumUseCase
    private lateinit var getAlbumInfoUseCase: GetAlbumInfoUseCase
    private lateinit var savedStatHandle: SavedStateHandle
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        searchAlbumUseCase = mockk<SearchAlbumUseCase>()
        getAlbumInfoUseCase = mockk<GetAlbumInfoUseCase>()
        savedStatHandle = SavedStateHandle(mapOf(ARTIST_ID to artist.id))
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when getAlbumsById is called, then searchAlbumUseCase fails`() = runTest {
        val page = 1

        coEvery { searchAlbumUseCase(artist.id, page) } returns Resource.Error(Error.Unknown)
        viewModel = AlbumListViewModel(savedStatHandle, searchAlbumUseCase, getAlbumInfoUseCase)

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(false, state.isLoading)
        assertEquals(UiError.Unknown, state.error)
    }

    @Test
    fun `when getAlbumsById is called, then searchAlbumUseCase returns success but getAlbumInfoUseCase fails`() =
        runTest {

            coEvery { searchAlbumUseCase(artist.id, 1) } returns Resource.Success(listOf(album))
            coEvery { getAlbumInfoUseCase(album.id) } returns Resource.Error(Error.Unauthorized)
            viewModel = AlbumListViewModel(savedStatHandle, searchAlbumUseCase, getAlbumInfoUseCase)

            testDispatcher.scheduler.advanceUntilIdle()

            val state = viewModel.state.value
            assertEquals(false, state.isLoading)
            assertEquals(listOf(album), state.albums)
            assertEquals(listOf(album), state.filteredAlbums)
            assertEquals(null, state.error)
        }

    @Test
    fun `when getAlbumsById is called, then searchAlbumUseCase & getAlbumInfoUseCase return success`() =
        runTest {
            coEvery { searchAlbumUseCase(artist.id, 1) } returns Resource.Success(listOf(album))
            coEvery { getAlbumInfoUseCase(album.id) } returns Resource.Success(album)
            viewModel = AlbumListViewModel(savedStatHandle, searchAlbumUseCase, getAlbumInfoUseCase)

            testDispatcher.scheduler.advanceUntilIdle()

            val state = viewModel.state.value
            assertEquals(false, state.isLoading)
            assertEquals(listOf(album), state.albums)
            assertEquals(listOf(album), state.filteredAlbums)
            assertEquals(null, state.error)
        }

    @Test
    fun `when onLoadMore is called, then searchAlbumUseCase fails`() = runTest {
        val firstPage = listOf(album)

        coEvery { searchAlbumUseCase(artist.id, 1) } returns Resource.Success(firstPage)
        coEvery { searchAlbumUseCase(artist.id, 2) } returns Resource.Error(Error.Unauthorized)
        coEvery { getAlbumInfoUseCase(album.id) } returns Resource.Success(album)
        viewModel = AlbumListViewModel(savedStatHandle, searchAlbumUseCase, getAlbumInfoUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onLoadMore()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(false, state.isLoading)
        assertEquals(false, state.isLoadingMore)
        assertEquals(true, state.canLoadMore)
        assertEquals(firstPage, state.albums)
        assertEquals(firstPage, state.filteredAlbums)
        assertEquals(UiError.Unauthorized, state.error)
    }

    @Test
    fun `when onLoadMore is called, then searchAlbumUseCase returns success`() = runTest {
        val albumId = "222"
        val genres = listOf("genre")
        val labels = listOf("label")
        val firstPage = listOf(album)
        val secondPage = listOf(album.copy(id = albumId, genres = genres, labels = labels))

        coEvery { searchAlbumUseCase(artist.id, 1) } returns Resource.Success(firstPage)
        coEvery { getAlbumInfoUseCase(album.id) } returns Resource.Success(
            album.copy(
                genres = genres, labels = labels
            )
        )
        coEvery { searchAlbumUseCase(artist.id, 2) } returns Resource.Success(secondPage)
        viewModel = AlbumListViewModel(savedStatHandle, searchAlbumUseCase, getAlbumInfoUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onLoadMore()
        testDispatcher.scheduler.advanceUntilIdle()

        val pages = listOf(
            album.copy(genres = genres, labels = labels),
            album.copy(id = albumId, genres = genres, labels = labels)
        )
        val state = viewModel.state.value
        assertEquals(false, state.isLoading)
        assertEquals(false, state.isLoadingMore)
        assertEquals(true, state.canLoadMore)
        assertEquals(pages, state.albums)
        assertEquals(pages, state.filteredAlbums)
        assertEquals(null, state.error)
    }

    @Test
    fun `when doSort is called with YEAR, then apply the correct filter`() {
        val album1 = album.copy(id = "1", year = 2000)
        val album2 = album.copy(id = "2", year = 1999)
        val albums = listOf(album1, album2)

        sortingCallTestSetup(albums)

        viewModel.doSort(AlbumSortType.YEAR)

        val state = viewModel.state.value
        assertEquals(listOf(album2, album1), state.filteredAlbums)
        assertEquals(AlbumSortType.YEAR, state.selectedSort)
    }

    @Test
    fun `when doSort is called with GENRE, then apply the correct filter`() {
        val album1 = album.copy(id = "1", genres = listOf("Rock"))
        val album2 = album.copy(id = "2", genres = listOf("Blues"))
        val albums = listOf(album1, album2)

        sortingCallTestSetup(albums)

        viewModel.doSort(AlbumSortType.GENRE)

        val state = viewModel.state.value
        assertEquals(listOf(album2, album1), state.filteredAlbums)
        assertEquals(AlbumSortType.GENRE, state.selectedSort)
    }

    @Test
    fun `when doSort is called with LABEL, then apply the correct filter`() {
        val album1 = album.copy(id = "1", labels = listOf("Sony"))
        val album2 = album.copy(id = "2", labels = listOf("Atlantic"))
        val albums = listOf(album1, album2)

        sortingCallTestSetup(albums)

        viewModel.doSort(AlbumSortType.LABEL)

        val state = viewModel.state.value
        assertEquals(listOf(album2, album1), state.filteredAlbums)
        assertEquals(AlbumSortType.LABEL, state.selectedSort)
    }

    private fun sortingCallTestSetup(albums: List<Album>) {
        coEvery { searchAlbumUseCase(artist.id, 1) } returns Resource.Success(albums)
        coEvery { getAlbumInfoUseCase(album.id) } returns Resource.Success(album)
        viewModel = AlbumListViewModel(savedStatHandle, searchAlbumUseCase, getAlbumInfoUseCase)

        testDispatcher.scheduler.advanceUntilIdle()
    }
}
