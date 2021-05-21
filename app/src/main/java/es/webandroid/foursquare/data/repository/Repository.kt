package es.webandroid.foursquare.data.repository

import es.webandroid.foursquare.core.error_handling.Failure
import es.webandroid.foursquare.core.functional.Either
import es.webandroid.foursquare.core.functional.getOrElse
import es.webandroid.foursquare.data.source.LocalDataSource
import es.webandroid.foursquare.data.source.RemoteDataSource
import es.webandroid.foursquare.domain.model.Venue
import es.webandroid.foursquare.domain.repository.Repository
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : Repository {

    override suspend fun getVenues(near: String, forceUpdate: Boolean): Either<Failure, List<Venue>> {
        if (localDataSource.isEmpty() || forceUpdate) {
            val response = remoteDataSource.getVenues(near)
            if (response.isRight) {
                localDataSource.clearData()
                localDataSource.saveVenues(response.getOrElse(emptyList()))
            }
            return response
        }

        return try {
            Either.Right(localDataSource.getVenues())
        } catch (e: Exception) {
            Either.Left(Failure.DBError)
        }
    }

    override suspend fun getVenueDetails(venueId: String): Either<Failure, Venue> {
        val localVenue = localDataSource.findById(venueId)
        if (localVenue.detailsLoaded) {
            return try {
                Either.Right(localVenue)
            } catch (e: Exception) {
                Either.Left(Failure.DBError)
            }
        }

        val remoteVenue = remoteDataSource.getVenueDetails(venueId)
        localDataSource.update(remoteVenue.getOrElse(localVenue))
        return remoteVenue
    }
}
