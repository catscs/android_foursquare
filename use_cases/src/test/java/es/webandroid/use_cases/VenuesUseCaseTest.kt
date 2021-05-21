package es.webandroid.use_cases

import com.nhaarman.mockitokotlin2.*
import es.webandroid.data.Repository
import es.webandroid.domain.Venue
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class VenuesUseCaseTest {
    private val repository: Repository = mock()
    private lateinit var sut: VenuesUseCase

    @Before
    fun setup() {
        sut = VenuesUseCase(repository)
    }

    @Test
    fun `venues are loaded correctly`() {
        runBlocking {
            // Prepare data
            val venues = listOf(Venue("1", "name"))
            whenever(repository.getVenues(any(), any())).doReturn(venues)
            // Result
            val result = sut.getVenues("City")
            // Check
            verify(repository).getVenues(any(), any()) // verificar que el metodo se ha llamdo
            verifyNoMoreInteractions(repository)
            assertEquals(result, venues)
        }
    }
}


