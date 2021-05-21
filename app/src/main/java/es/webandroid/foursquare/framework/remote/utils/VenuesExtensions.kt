package es.webandroid.foursquare.framework.remote.utils

import es.webandroid.foursquare.domain.model.VenueResponseData


fun VenueResponseData.formatCategories(): String = categories.joinToString(" / ") { it.name ?: "" }

fun VenueResponseData.getAddress(): String? {
    return location.address
}
