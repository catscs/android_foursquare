package es.webandroid.foursquare.domain.use_cases


import es.webandroid.foursquare.core.error_handling.Failure
import es.webandroid.foursquare.core.functional.Either
import es.webandroid.foursquare.domain.model.Venue
import es.webandroid.foursquare.domain.repository.Repository
import es.webandroid.foursquare.domain.use_cases.VenueDetailUseCase.Params
import javax.inject.Inject

class VenueDetailUseCase @Inject constructor(
    private val repository: Repository
) : UseCase<Venue, Params>() {

    override suspend fun run(params: Params): Either<Failure, Venue> = repository.getVenueDetails(params.venueId)

    data class Params(val venueId: String)
}
