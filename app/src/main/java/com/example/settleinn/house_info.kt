package com.example.settleinn

data class HouseDetail(
    val address: String,
    val price: Int,
    val zestimate: Int?,
    val imgSrc: String,
    val bedrooms: Int,
    val bathrooms: Int,
    val livingArea: Int,
    val listingStatus: String,
    val daysOnZillow: String?
)
