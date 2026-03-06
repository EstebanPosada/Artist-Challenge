package com.estebanposada.domain.usecase

import com.estebanposada.domain.Error
import com.estebanposada.domain.Resource
import com.estebanposada.domain.model.Artist
import com.estebanposada.domain.repository.FakeRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetArtistDetailUseCaseTest {
    private lateinit var getArtistDetailUseCase: GetArtistDetailUseCase
    private lateinit var repository: FakeRepository

    @Before
    fun setUp() {
        repository = FakeRepository()
        getArtistDetailUseCase = GetArtistDetailUseCase(repository)
    }

    @Test
    fun `when getArtistDetailUseCase is called and repo returns error`() = runTest {
        val id = "id"
        val error = Error.Unknown

        repository.artist = Resource.Error(error)
        val result = getArtistDetailUseCase(id)

        assertEquals(Resource.Error(error), result)
    }

    @Test
    fun `when getArtistDetailUseCase is called, then repository returns successfully`() = runTest {
        val id = "id"
        val artist = Artist(id = "12", name = "name")

        repository.artist = Resource.Success(artist)
        val result = getArtistDetailUseCase(id)

        assertEquals(Resource.Success(artist), result)
    }
}
