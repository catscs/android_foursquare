package es.webandroid.foursquare.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VenueResponseData(
    val id: String,
    val name: String,
    val location: VenueLocation,
    val categories: List<VenueCategory>,
    val contact: VenueContactInfo? = null,
    val rating: Float? = null,
    val bestPhoto: VenuePhotoEntity? = null,
    var detailsLoaded : Boolean = false
) {
    companion object {
        val empty = VenueResponseData("", "", VenueLocation(""), emptyList())
    }
}

@JsonClass(generateAdapter = true)
data class VenueLocation(val address: String?)

@JsonClass(generateAdapter = true)
data class VenuePhotoEntity(
    val prefix: String,
    val suffix: String,
    val width: String,
    val height: String
) {
    val photoUrl: String get() = prefix + width + "x" + height + suffix;
}

@JsonClass(generateAdapter = true)
data class VenueCategory(val name: String?)

@JsonClass(generateAdapter = true)
data class VenueContactInfo(
    val formattedPhone: String?,
    val twitter: String?,
    val instagram: String?,
    val facebookName: String?
)
