package com.example.travel_help

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.board.*

class BoardActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.board)

        val title = intent.getStringExtra("title")

        board_boardtitle.setText(title)
    }
}