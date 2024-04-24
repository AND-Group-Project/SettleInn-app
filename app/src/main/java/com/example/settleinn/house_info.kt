package com.example.settleinn

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class ApiResponse(val props: List<HouseDetail>)
@Entity(tableName = "house_table")
@Keep
@Serializable
data class HouseDetail(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @SerialName("zpid") val zpid: Int,
    @SerialName("address") val address: String?,
    @SerialName("price") val price: Int?,
    @SerialName("zestimate") val zestimate: Int?,
    @SerialName("imgSrc") val imgSrc: String?,
    @SerialName("bedrooms") val bedrooms: Int?,
    @SerialName("bathrooms") val bathrooms: Int?,
    @SerialName("livingArea") val livingArea: Int?,
    @SerialName("listingStatus") val listingStatus: String?,
    @SerialName("daysOnZillow") val daysOnZillow: String?,
) : java.io.Serializable {

}
