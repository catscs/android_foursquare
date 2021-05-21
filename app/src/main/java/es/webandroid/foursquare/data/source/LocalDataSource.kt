package es.webandroid.foursquare.data.source

import es.webandroid.foursquare.domain.model.Venue


interface LocalDataSource {
    suspend fun isEmpty(): Boolean
    suspend fun saveVenues(venues: List<Venue>)
    suspend fun getVenues(): List<Venue>
    suspend fun findById(venueId: String): Venue
    suspend fun update(venue: Venue)
    suspend fun clearData()
}
