package es.webandroid.foursquare.presentation.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.webandroid.foursquare.core.connectivity.base.ConnectivityProvider
import es.webandroid.foursquare.core.error_handling.Failure
import es.webandroid.foursquare.core.extensions.hasInternet
import es.webandroid.foursquare.core.platform.BaseViewModel
import es.webandroid.foursquare.domain.model.Venue
import es.webandroid.foursquare.domain.use_cases.VenuesUseCase

class VenueListViewModel @ViewModelInject constructor(
    private val connectivityProvider: ConnectivityProvider,
    private val venuesUseCase: VenuesUseCase
) : BaseViewModel() {

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event>
        get() = _event

    private var lastInputNear: String? = null

    fun onVenueClicked(venueId: String) {
        _event.value = Event.Detail(venueId)
    }

    fun searchVenues(near: String?) {
        if (near.isNullOrEmpty()) return
        val hasInternet = connectivityProvider.getNetworkState().hasInternet()
        if (!hasInternet && lastInputNear != near) {
            handleFailure(Failure.NetworkError.NoConnection)
            return
        }
        _event.value = Event.Loading
        val fortUpdate = hasInternet && lastInputNear != near
        venuesUseCase(VenuesUseCase.Params(near, fortUpdate)) { it.fold(::handleFailure, ::handleResult) }
        lastInputNear = near
    }

    override fun handleFailure(failure: Failure) {
        super.handleFailure(failure)
        _event.value = Event.Content(emptyList())
    }

    private fun handleResult(venues: List<Venue>) {
        _event.value = Event.Content(venues)
    }

    fun onFragmentViewCreated() {
        _event.value = Event.Init
        searchVenues(lastInputNear)
    }

    sealed class Event {
        object Init : Event()
        object Loading : Event()
        data class Detail(val venueId: String) : Event()
        data class Content(val venues: List<Venue>) : Event()
    }
}
