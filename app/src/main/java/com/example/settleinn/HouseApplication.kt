package com.example.settleinn

import android.app.Application

class HouseApplication : Application() {
    val db by lazy { AppDatabase.getInstance(this) }
}