package es.webandroid.foursquare.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VenueEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val address: String? = null,
    val categories: String? = null,
    val photoUrl: String? = null,
    val rating: Float? = null,
    val phone: String? = null,
    val twitter: String? = null,
    val instagram: String? = null,
    val facebook: String? = null,
    var detailsLoaded: Boolean = false
)
