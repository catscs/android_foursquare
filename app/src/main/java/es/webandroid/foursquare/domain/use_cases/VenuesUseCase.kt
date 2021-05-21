package es.webandroid.foursquare.domain.use_cases

import es.webandroid.foursquare.domain.model.Venue
import es.webandroid.foursquare.domain.repository.Repository
import es.webandroid.foursquare.domain.use_cases.VenuesUseCase.Params
import javax.inject.Inject

class VenuesUseCase @Inject constructor(private val repository: Repository): UseCase<List<Venue>, Params>() {

    override suspend fun run(params: Params) = repository.getVenues(params.near, params.forceUpdate)

    data class Params(val near: String, val forceUpdate: Boolean = false)
}
