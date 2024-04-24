package com.example.settleinn

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HouseDao {
    @Query("SELECT * FROM house_table")
    fun getAll(): List<HouseDetail>

    @Insert
    fun insert(house: HouseDetail)

    @Delete
    fun deleteItem(house: HouseDetail)

    @Query("DELETE FROM house_table")
    fun deleteAll()

    // TODO: ADD MORE METHODS HERE
}