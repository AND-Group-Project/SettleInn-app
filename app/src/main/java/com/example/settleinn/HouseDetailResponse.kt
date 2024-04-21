package com.example.settleinn

// class to store all the information we get from calling houseDetail Api

data class SchoolInfo(
    val link: String,
    val rating: Int,
    val distance: Double,
    val name: String,
    val type: String
)

data class HouseInfo(
    val imageUrl: String,
    val price: Int,
    val bedrooms: Int,
    val bathrooms: Int,
    val area: Int
)
