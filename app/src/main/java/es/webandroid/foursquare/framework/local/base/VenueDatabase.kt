package es.webandroid.foursquare.framework.local.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.webandroid.foursquare.domain.model.VenueEntity

@Database(entities = [VenueEntity::class], version = 1)
abstract class VenueDatabase: RoomDatabase() {

    abstract fun venueDao(): VenueDao

    companion object {
        fun build(context: Context) = Room.databaseBuilder(context, VenueDatabase::class.java, "venue_db").build()

    }
}
