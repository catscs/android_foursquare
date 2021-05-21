package es.webandroid.foursquare.framework.local.base

import es.webandroid.foursquare.domain.model.Contact
import es.webandroid.foursquare.domain.model.Venue
import es.webandroid.foursquare.domain.model.VenueEntity

fun Venue.toRoomVenue(): VenueEntity =
    VenueEntity(
        id,
        name,
        address,
        categories,
        photoUrl,
        rating,
        phone = contactInfo?.phone,
        twitter = contactInfo?.twitter,
        instagram = contactInfo?.instagram,
        facebook = contactInfo?.facebook,
        detailsLoaded = detailsLoaded
    )

fun VenueEntity.toDataVenue(): Venue =
    Venue(
        id,
        name,
        address,
        categories,
        photoUrl,
        rating,
        Contact(phone, twitter, instagram, facebook),
        detailsLoaded = detailsLoaded
    )
