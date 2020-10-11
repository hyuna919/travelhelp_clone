package com.example.travel_help.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.travel_help.R
import kotlinx.android.synthetic.main.calendar.*
import java.io.FileInputStream
import java.io.FileOutputStream

class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar)
    }
}