package com.estebanposada.domain.usecase

import com.estebanposada.domain.Error
import com.estebanposada.domain.Resource
import com.estebanposada.domain.model.Artist
import com.estebanposada.domain.repository.FakeRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SearchArtistsUseCaseTest {
    private lateinit var searchArtistsUseCase: SearchArtistsUseCase
    private lateinit var repository: FakeRepository

    @Before
    fun setUp() {
        repository = FakeRepository()
        searchArtistsUseCase = SearchArtistsUseCase(repository)
    }

    @Test
    fun `when searchArtistsUseCase is called and repo returns error`() = runTest {
        val id = "id"
        val page = 1
        val error = Error.Unknown

        repository.artistList = Resource.Error(error)
        val result = searchArtistsUseCase(id, page)

        assertEquals(Resource.Error(error), result)
    }

    @Test
    fun `when searchArtistsUseCase is called, then repository returns successfully`() = runTest {
        val id = "id"
        val page = 1
        val artist = Artist(id = "12", name = "name")

        repository.artistList = Resource.Success(listOf(artist))
        val result = searchArtistsUseCase(id, page)

        assertEquals(Resource.Success(listOf(artist)), result)
    }
}
