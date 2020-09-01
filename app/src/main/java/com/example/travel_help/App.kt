package com.example.travel_help

import android.app.Application
import com.example.travel_help.DataClass.DataClassSharedPreferences

class App:Application() {
    companion object{
        lateinit var prefs: DataClassSharedPreferences
    }

    override fun onCreate() {
        prefs = DataClassSharedPreferences(this)
        super.onCreate()
    }
}