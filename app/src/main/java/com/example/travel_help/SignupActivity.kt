package com.example.travel_help

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toolbar


class SignupActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        val tb = findViewById<View>(R.id.board_tb) as Toolbar
        //setSupportActionBar(tb)

    }
}