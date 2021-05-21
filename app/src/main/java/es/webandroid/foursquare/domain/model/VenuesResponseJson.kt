package es.webandroid.foursquare.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class VenuesResponseJson(val response: VenuesJson)

@JsonClass(generateAdapter = true)
class VenuesJson(val venues: List<VenueResponseData>)
