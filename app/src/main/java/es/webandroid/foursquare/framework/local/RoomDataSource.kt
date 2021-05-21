package es.webandroid.foursquare.framework.local

import es.webandroid.foursquare.data.source.LocalDataSource
import es.webandroid.foursquare.domain.model.Venue
import es.webandroid.foursquare.framework.local.base.VenueDatabase
import es.webandroid.foursquare.framework.local.base.toDataVenue
import es.webandroid.foursquare.framework.local.base.toRoomVenue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomDataSource @Inject constructor(
    private val venueDB: VenueDatabase
) : LocalDataSource {

    private val venueDao = venueDB.venueDao()

    override suspend fun isEmpty() = withContext(Dispatchers.IO) { venueDao.venueCount() <= 0 }

    override suspend fun saveVenues(venues: List<Venue>) = withContext(Dispatchers.IO) { venueDao.insertVenues(venues.map { it.toRoomVenue() }) }

    override suspend fun getVenues() = withContext(Dispatchers.IO) { venueDao.getAll().map { it.toDataVenue() } }

    override suspend fun findById(venueId: String) = withContext(Dispatchers.IO) { venueDao.findById(venueId).toDataVenue() }

    override suspend fun update(venue: Venue) = withContext(Dispatchers.IO) { venueDao.updateVenue(venue.toRoomVenue()) }

    override suspend fun clearData() = withContext(Dispatchers.IO) { venueDao.clearData() }

}
