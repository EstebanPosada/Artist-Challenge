package com.estebanposada.artischallenge.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.estebanposada.artischallenge.ui.artist
import com.estebanposada.artischallenge.ui.detail.ArtistDetailViewModel.Companion.ARTIST_ID
import com.estebanposada.domain.Resource
import com.estebanposada.domain.usecase.GetArtistDetailUseCase
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
class ArtistDetailViewModelTest {
    private lateinit var viewModel: ArtistDetailViewModel
    private lateinit var getArtistDetailUseCase: GetArtistDetailUseCase
    private lateinit var savedStatHandle: SavedStateHandle
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        getArtistDetailUseCase = mockk<GetArtistDetailUseCase>()
        savedStatHandle = SavedStateHandle(mapOf(ARTIST_ID to artist.id))
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when loadArtistDetail is called, then getArtistDetailUseCase fails`() = runTest {
        val id = artist.id
        val errorMsg = "error"

        coEvery { getArtistDetailUseCase(id) } returns Resource.Error(Throwable(errorMsg))
        viewModel = ArtistDetailViewModel(getArtistDetailUseCase, savedStatHandle)

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(false, state.isLoading)
        assertEquals(null, state.artist)
        assertEquals(errorMsg, state.error)
    }

    @Test
    fun `when loadArtistDetail is called, then getArtistDetailUseCase returns success`() = runTest {
        val id = artist.id

        coEvery { getArtistDetailUseCase(id) } returns Resource.Success(artist)
        viewModel = ArtistDetailViewModel(getArtistDetailUseCase, savedStatHandle)

        testDispatcher.scheduler.advanceUntilIdle()
        val state = viewModel.state.value
        assertEquals(false, state.isLoading)
        assertEquals(artist, state.artist)
        assertEquals(null, state.error)
    }
}
