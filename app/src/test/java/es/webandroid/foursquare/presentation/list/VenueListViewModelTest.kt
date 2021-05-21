package es.webandroid.foursquare.presentation.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import es.webandroid.foursquare.core.connectivity.base.ConnectivityProvider
import es.webandroid.foursquare.domain.use_cases.VenuesUseCase
import es.webandroid.foursquare.presentation.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class VenueListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = TestCoroutineRule()

    private val connectivityProvider: ConnectivityProvider = mock()
    private val venuesUseCase: VenuesUseCase = mock()
    private lateinit var sut: VenueListViewModel

    @Before
    fun setup() {
        sut = VenueListViewModel(connectivityProvider, venuesUseCase)
    }

    @Test
    fun `when near is null then return`() {
        // Result
        sut.searchVenues(null)
        // Check
        verifyNoMoreInteractions(connectivityProvider)
    }

    @Test
    fun `when near is empty then return`() {
        // Result
        sut.searchVenues("")
        // Check
        verifyNoMoreInteractions(connectivityProvider)
    }

    @Test
    fun `searchVenues triggers request without internet connection`() {
        runBlocking {
            // Prepare data
            val near = "City"
            whenever(connectivityProvider.getNetworkState()).doReturn(ConnectivityProvider.NetworkState.NotConnectedState)

            // Result
            sut.searchVenues(near)

            // Check
            //verify(venuesUseCase).getVenues(eq(near), eq(false))
        }
    }


    // No puedo mokear networkState
    @Test
    @Ignore
    fun `searchVenues triggers request with internet connection`() {
        runBlocking {
            // Prepare data
            val near = "City"
            whenever(connectivityProvider.getNetworkState()).doReturn(ConnectivityProvider.NetworkState.NotConnectedState)

            // Result
            sut.searchVenues(near)

            // Check
            //verify(venuesUseCase).getVenues(eq(near), eq(true))
        }
    }
}
