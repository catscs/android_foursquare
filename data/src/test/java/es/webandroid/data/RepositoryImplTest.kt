package es.webandroid.data

import com.nhaarman.mockitokotlin2.*
import es.webandroid.common.Either
import es.webandroid.common.Failure
import es.webandroid.common.getOrElse
import es.webandroid.data.sources.LocalDataSource
import es.webandroid.data.sources.RemoteDataSource
import es.webandroid.domain.Venue
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Error

@RunWith(MockitoJUnitRunner::class)
class RepositoryImplTest {

    private val remoteDataSource: RemoteDataSource = mock()
    private val localDataSource: LocalDataSource = mock()
    private lateinit var sut: RepositoryImpl

    @Before
    fun setup()  {
        sut = RepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `getVenues saves remote data to local`() {
        runBlocking {
            // Prepare data
            val venues = Either.Right(listOf(Venue("1", "name")))

            whenever(localDataSource.isEmpty()).doReturn(true)
            whenever(remoteDataSource.getVenues(any())).doReturn(venues)

            // Result
            sut.getVenues("City")

            // Check
            verify(localDataSource).clearData()
            verify(localDataSource).saveVenues(venues.getOrElse(listOf()))
        }
    }

    @Test
    fun `where data base no es empty and get venues`() {
        runBlocking {
            // Prepare data
            val venues = listOf(Venue("1", "name"))
            whenever(localDataSource.isEmpty()).doReturn(false)
            whenever(localDataSource.getVenues()).doReturn(venues)

            // Result
            val result = sut.getVenues("City")

            // Check
            verify(localDataSource).getVenues()
            Assert.assertEquals(result.getOrElse(listOf()), venues)
        }
    }

    @Test
    fun `when there is local data but forceUpdate is true then remote data to local`() {
        runBlocking {
            // Prepare data
            val venues = Either.Right(listOf(Venue("1", "name")))

            whenever(localDataSource.isEmpty()).doReturn(false)
            whenever(remoteDataSource.getVenues(any())).doReturn(venues)

            // Result
            sut.getVenues("City", true)

            // Check
            verify(localDataSource).clearData()
            verify(localDataSource).saveVenues(venues.getOrElse(listOf()))
        }
    }


}


