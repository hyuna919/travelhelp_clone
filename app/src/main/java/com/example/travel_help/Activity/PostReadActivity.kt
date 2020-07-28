package com.example.travel_help.Activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.travel_help.DataClass.DataClassPost
import com.example.travel_help.R
import kotlinx.android.synthetic.main.post_read.*


class PostReadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_read)
        //val title = intent.getParcelableExtra<DataClassPost>("title")
        val title = intent.getStringExtra("title")

        pr_title?.text = title
        //pr_date?.text = post.date.toString()
        //pr_airport?.text = post.airport
        //pr_contents?.text = post.content

    }
}