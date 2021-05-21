package es.webandroid.foursquare.domain.repository

import es.webandroid.foursquare.core.error_handling.Failure
import es.webandroid.foursquare.core.functional.Either
import es.webandroid.foursquare.domain.model.Venue

interface Repository {
    suspend fun getVenues(near: String, forceUpdate: Boolean = false): Either<Failure, List<Venue>>
    suspend fun getVenueDetails(venueId: String): Either<Failure, Venue>
}
