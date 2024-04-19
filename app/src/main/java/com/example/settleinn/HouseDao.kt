package com.example.settleinn

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface HouseDao {
    @Query("SELECT * FROM house_table")
    fun getAll(): Flow<List<House>>

    @Insert
    fun insert(house: House)

    @Delete
    fun deleteItem(house: House)

    @Query("DELETE FROM house_table")
    fun deleteAll()

    // TODO: ADD MORE METHODS HERE
}