package es.webandroid.foursquare.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class VenueDetailsResponseJson(val response: VenueJson)

@JsonClass(generateAdapter = true)
class VenueJson(val venue: VenueResponseData)
