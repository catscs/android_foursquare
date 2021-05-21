package es.webandroid.foursquare.framework.local.base

import androidx.room.*
import es.webandroid.foursquare.domain.model.VenueEntity

@Dao
interface VenueDao {

    @Query("SELECT * FROM VenueEntity")
    fun getAll(): List<VenueEntity>

    @Query("SELECT * FROM VenueEntity WHERE id = :id")
    fun findById(id: String): VenueEntity

    @Query("SELECT COUNT(id) FROM VenueEntity")
    fun venueCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVenues(venues: List<VenueEntity>)

    @Update
    fun updateVenue(venue: VenueEntity)

    @Query("DELETE FROM VenueEntity")
    fun clearData()
}
