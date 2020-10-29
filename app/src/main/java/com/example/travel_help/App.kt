package com.example.travel_help

import android.app.Application
import com.example.travel_help.DataClass.DataClassSchedule
import com.example.travel_help.DataClass.DataClassSharedPreferences

class App:Application() {
    companion object{
        lateinit var prefs: DataClassSharedPreferences
        lateinit var schedule: DataClassSchedule
    }

    override fun onCreate() {
        prefs = DataClassSharedPreferences(this)
        schedule = DataClassSchedule("추가",null)
        super.onCreate()
    }
}