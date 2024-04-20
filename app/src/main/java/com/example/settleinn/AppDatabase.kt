//package com.example.settleinn
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import com.example.settleinn.House
//
//@Database(entities = [House::class], version = 1)
//abstract class AppDatabase : RoomDatabase() {
//
//    abstract fun houseDao(): HouseDao
//
//    companion object {
//
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        fun getInstance(context: Context): AppDatabase =
//            INSTANCE ?: synchronized(this) {
//                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
//            }
//
//        private fun buildDatabase(context: Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                AppDatabase::class.java, "House-db"
//            ).build()
//    }
//}