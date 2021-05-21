package es.webandroid.foursquare.framework.remote


import es.webandroid.foursquare.domain.model.VenueDetailsResponseJson
import es.webandroid.foursquare.domain.model.VenuesResponseJson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface NetworkApi {
    companion object {
        const val SEARCH = "venues/search"
        const val VENUE_DETAILS = "venues/{venue_id}"
        const val PARAM_NEAR = "near"
        const val PARAM_LIMIT = "limit"
        const val PARAM_RADIUS = "radius"
        const val PARAM_VENUE_ID = "venue_id"
        const val VENUES_LIMIT = 3 // TODO: Change to 10
        const val RADIUS = 1000.0
    }

    @GET(SEARCH)
    suspend fun getVenues(
        @Query(PARAM_NEAR) near: String,
        @Query(PARAM_LIMIT) limit: Int = VENUES_LIMIT,
        @Query(PARAM_RADIUS) radius: Double = RADIUS
    ): Response<VenuesResponseJson>

    @GET(VENUE_DETAILS)
    suspend fun getVenueDetails(
        @Path(PARAM_VENUE_ID) venueId: String
    ): Response<VenueDetailsResponseJson>
}
