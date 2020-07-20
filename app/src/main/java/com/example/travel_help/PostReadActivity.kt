package com.example.travel_help

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.board.*
import kotlinx.android.synthetic.main.msg_list.*
import kotlinx.android.synthetic.main.post_read.*


class PostReadActivity : AppCompatActivity() {

    //리사이클러뷰 더미데이터
    val dummy = arrayListOf<DataClassPost>(
        DataClassPost("제목", 20200616,"frankfurt","앙뇽하세요내용입니다블라블라블ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ라")    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_read)

        pr_title?.text = dummy[0].title
        pr_date?.text = dummy[0].date.toString()
        pr_airport?.text = dummy[0].airport
        pr_contents?.text = dummy[0].content


    }



}