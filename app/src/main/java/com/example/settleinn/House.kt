package com.example.settleinn

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "house_table")

data class House {
    // TODO ADD MORE ATTRIBUTES
    @ColumnInfo(name = "price") val price: Int?,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "numBedroom") val numBedroom: Int?,
    @ColumnInfo(name = "status") val status: String?,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
}