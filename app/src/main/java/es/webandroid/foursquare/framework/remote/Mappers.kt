package es.webandroid.foursquare.framework.remote

import es.webandroid.foursquare.domain.model.Contact
import es.webandroid.foursquare.domain.model.Venue
import es.webandroid.foursquare.domain.model.VenueContactInfo
import es.webandroid.foursquare.domain.model.VenueResponseData
import es.webandroid.foursquare.framework.remote.utils.formatCategories
import es.webandroid.foursquare.framework.remote.utils.getAddress


interface MapperTo<in T, out R> {
    fun mapTo(t: T): R
}

class VenueMapper : MapperTo<VenueResponseData, Venue> {
    override fun mapTo(t: VenueResponseData): Venue =
        Venue(t.id, t.name, t.getAddress())
}

class VenueDetailMapper :
    MapperTo<VenueResponseData, Venue> {
    override fun mapTo(t: VenueResponseData) =
        Venue(
            t.id,
            t.name,
            t.getAddress(),
            t.formatCategories(),
            t.bestPhoto?.photoUrl,
            t.rating,
            mapContactInfo(t.contact)
        )

    private fun mapContactInfo(venueContactInfo: VenueContactInfo?): Contact? {
        return if (venueContactInfo != null) {
            Contact(
                venueContactInfo.formattedPhone,
                venueContactInfo.twitter,
                venueContactInfo.instagram,
                venueContactInfo.facebookName
            )
        } else null
    }
}
