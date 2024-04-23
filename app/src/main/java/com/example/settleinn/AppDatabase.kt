package com.example.settleinn

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [House::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun houseDao(): HouseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "House-db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}