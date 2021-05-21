package es.webandroid.foursquare.domain.model

data class Venue(
    val id: String,
    val name: String,
    val address: String? = null,
    val categories: String? = null,
    val photoUrl: String? = null,
    val rating: Float? = null,
    val contactInfo: Contact? = null,
    var detailsLoaded: Boolean = false
)

data class Contact(
    val phone: String?,
    val twitter: String?,
    val instagram: String?,
    val facebook: String?
)
