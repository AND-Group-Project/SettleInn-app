package com.example.settleinn

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class HouseDetail(
    @SerialName("address")
    val address: String,
    @SerialName("price")
    val price: Int,
    @SerialName("zestimate")
    val zestimate: Int?,
    @SerialName("imgSrc")
    val imgSrc: String,
    @SerialName("bedrooms")
    val bedrooms: Int,
    @SerialName("bathrooms")
    val bathrooms: Int,
    @SerialName("livingArea")
    val livingArea: Int,
    @SerialName("listingStatus")
    val listingStatus: String,
    @SerialName("daysOnZillow")
    val daysOnZillow: String?
): java.io.Serializable {

}
