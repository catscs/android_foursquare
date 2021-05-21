package es.webandroid.foursquare.data.source


import es.webandroid.foursquare.core.error_handling.Failure
import es.webandroid.foursquare.core.functional.Either
import es.webandroid.foursquare.domain.model.Venue

interface RemoteDataSource {
    suspend fun getVenues(near: String): Either<Failure, List<Venue>>
    suspend fun getVenueDetails(venueId: String): Either<Failure, Venue>
}
