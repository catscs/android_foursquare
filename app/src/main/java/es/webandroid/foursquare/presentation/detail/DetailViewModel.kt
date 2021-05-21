package es.webandroid.foursquare.presentation.detail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import es.webandroid.foursquare.core.platform.BaseViewModel
import es.webandroid.foursquare.domain.model.Venue
import es.webandroid.foursquare.domain.use_cases.VenueDetailUseCase
import es.webandroid.foursquare.domain.use_cases.VenueDetailUseCase.Params

class DetailViewModel @ViewModelInject constructor(
    private val venueDetailUseCase: VenueDetailUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event>
        get() = _event

    fun getVenueDetail(venueId: String) {
        if (venueId.isEmpty()) return
        venueDetailUseCase(Params(venueId)) { it.fold(::handleFailure, ::handleResult) }
    }

    private fun handleResult(venue: Venue) {
        _event.value = Event.Content(venue)
    }

    sealed class Event {
        data class Content(val venue: Venue) : Event()
    }
}
