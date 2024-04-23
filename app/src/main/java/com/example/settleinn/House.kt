package com.example.settleinn

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "house_table")
data class House(
    // TODO ADD MORE ATTRIBUTES
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "price") val price: Int?,
    @ColumnInfo(name = "zestimate") val zestimate: Int?,
    @ColumnInfo(name = "imgSrc") val imgSrc: String?,
    @ColumnInfo(name = "bedrooms") val bedrooms: Int?,
    @ColumnInfo(name = "bathrooms") val bathrooms: Int?,
    @ColumnInfo(name = "livingArea") val livingArea: Int?,
    @ColumnInfo(name = "listingStatus") val listingStatus: String?,
    @ColumnInfo(name = "daysOnZillow") val daysOnZillow: String?,
)