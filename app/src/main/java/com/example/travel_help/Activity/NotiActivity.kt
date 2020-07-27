package com.example.travel_help.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travel_help.DataClass.DataClassNotification
import com.example.travel_help.RecyclerViewAdapter.NotiRvAdapter
import com.example.travel_help.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.notification.*


class NotiActivity : AppCompatActivity() {
    private lateinit var textMessage: TextView
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.navigation_massege -> {
                val intent = Intent(this, MsgListActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.navigation_noti -> {
                val intent = Intent(this, NotiActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.navigation_mypage -> {
                val intent = Intent(this, MypageActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        false
    }

    //리사이클러뷰 더미데이터
    val dummy = arrayListOf<DataClassNotification>(
        DataClassNotification("김뫄뫄", "안녕하세요"),
        DataClassNotification("Franfran", "hello:)")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notification)
        val navView: BottomNavigationView = findViewById(R.id.board_nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        //리사이클러뷰 어댑터
        //val intent = Intent(this, BoardActivity::class.java)
        val mAdapter = NotiRvAdapter(this, dummy) {
            //country ->startActivity(intent)}//(Intent(this, BoardActivity::class.java))}
            //country ->
            //intent.putExtra("title", country.countryName)
            //startActivity(intent)
        }
        noti_rv.adapter = mAdapter

        //리사이클러뷰 레이아웃매니저
        val lm = LinearLayoutManager(this)
        noti_rv.layoutManager = lm
        noti_rv.setHasFixedSize(true)
    }
}