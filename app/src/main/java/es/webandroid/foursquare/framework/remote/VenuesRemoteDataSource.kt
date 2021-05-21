package es.webandroid.foursquare.framework.remote


import es.webandroid.foursquare.core.error_handling.Failure
import es.webandroid.foursquare.core.error_handling.Failure.NetworkError
import es.webandroid.foursquare.core.error_handling.Failure.NetworkError.Fatal
import es.webandroid.foursquare.core.functional.Either
import es.webandroid.foursquare.data.source.RemoteDataSource
import es.webandroid.foursquare.domain.model.Venue
import es.webandroid.foursquare.domain.model.VenueResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class VenuesRemoteDataSource @Inject constructor(
    private val networkApi: NetworkApi,
    private val venueMapper: VenueMapper,
    private val venueDetailMapper: VenueDetailMapper
) : RemoteDataSource {
    override suspend fun getVenues(near: String): Either<Failure, List<Venue>> = withContext(Dispatchers.IO) {
        val response = networkApi.getVenues(near)
        handleResponse(
            response,
            data = response.body()?.response?.venues,
            transform = { it.map { venue -> venueMapper.mapTo(venue) } },
            default = emptyList()
        )
    }

    override suspend fun getVenueDetails(venueId: String): Either<Failure, Venue> = withContext(Dispatchers.IO) {
        val response = networkApi.getVenueDetails(venueId)
        handleResponse(
            response,
            data = response.body()?.response?.venue,
            transform = { venueDetailMapper.mapTo(it) },
            default = VenueResponseData.empty
        )
    }

    private fun <Json, D, R> handleResponse(response: Response<Json>, data: D?, transform: (D) -> R, default: D): Either<Failure, R> {
        return when (response.isSuccessful) {
            true -> Either.Right(transform(data ?: default))
            false -> Either.Left(getNetworkError(response.code(), response.message()))
        }
    }

    private fun getNetworkError(statusCode: Int, message: String?): Failure {
        return when (statusCode) {
            in 400..499 -> Fatal(message)
            else -> NetworkError.Recoverable(message)
        }
    }
}
