package com.estebanposada.artischallenge.ui.search

import com.estebanposada.artischallenge.ui.artist
import com.estebanposada.artischallenge.ui.common.UiError
import com.estebanposada.domain.Error
import com.estebanposada.domain.Resource
import com.estebanposada.domain.usecase.SearchArtistsUseCase
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
class SearchArtistViewModelTest {
    private lateinit var viewModel: SearchArtistViewModel
    private lateinit var searchArtistsUseCase: SearchArtistsUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        searchArtistsUseCase = mockk<SearchArtistsUseCase>()
        viewModel = SearchArtistViewModel(searchArtistsUseCase)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when onSearch is called, then searchArtistsUseCase fails`() = runTest {
        val query = "q"
        val page = 1

        coEvery { searchArtistsUseCase(query, page) } returns Resource.Error(Error.Unauthorized)

        viewModel.onSearch(query)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(false, state.isLoading)
        assertEquals(UiError.Unauthorized, state.error)
    }

    @Test
    fun `when onSearch is called, then searchArtistsUseCase returns success`() = runTest {
        val query = "q"
        val page = 1
        coEvery { searchArtistsUseCase(query, page) } returns Resource.Success(listOf(artist))

        viewModel.onSearch(query)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(query, state.query)
        assertEquals(listOf(artist), state.artists)
        assertEquals(false, state.isLoading)
        assertEquals(true, state.canLoadMore)
        assertEquals(null, state.error)
    }

    @Test
    fun `when onLoadNextPage is called, then searchArtistsUseCase fails`() = runTest {
        val query = "q"
        val firstPage = listOf(artist)

        coEvery { searchArtistsUseCase(query, 1) } returns Resource.Success(firstPage)
        coEvery { searchArtistsUseCase(query, 2) } returns Resource.Error(Error.Unauthorized)

        viewModel.onSearch(query)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onLoadNextPage()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(query, state.query)
        assertEquals(firstPage, state.artists)
        assertEquals(false, state.isLoading)
        assertEquals(false, state.isLoadingMore)
        assertEquals(true, state.canLoadMore)
        assertEquals(UiError.Unauthorized, state.error)
    }

    @Test
    fun `when onLoadNextPage is called, then searchArtistsUseCase returns success`() = runTest {
        val query = "q"
        val firstPage = listOf(artist)
        val secondPage = listOf(artist.copy(id = "222", name = "other"))

        coEvery { searchArtistsUseCase(query, 1) } returns Resource.Success(firstPage)
        coEvery { searchArtistsUseCase(query, 2) } returns Resource.Success(secondPage)

        viewModel.onSearch(query)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onLoadNextPage()
        testDispatcher.scheduler.advanceUntilIdle()

        val pages = firstPage + secondPage
        val state = viewModel.state.value
        assertEquals(query, state.query)
        assertEquals(pages, state.artists)
        assertEquals(false, state.isLoading)
        assertEquals(true, state.canLoadMore)
        assertEquals(null, state.error)
    }
}
