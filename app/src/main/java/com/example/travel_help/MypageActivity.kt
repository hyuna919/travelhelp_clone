package com.example.travel_help

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.msg_list.*
import kotlinx.android.synthetic.main.mypage.*


class MypageActivity : AppCompatActivity() {
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
    val dummy = arrayListOf<DataClassMypage>(
        DataClassMypage("비밀번호 변경"),
        DataClassMypage("회원탈퇴")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.msg_list)
        val navView: BottomNavigationView = findViewById(R.id.board_nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        //리사이클러뷰 어댑터
        //val intent = Intent(this, BoardActivity::class.java)
        val mAdapter = MypageRvAdapter(this, dummy) {
            //country ->startActivity(intent)}//(Intent(this, BoardActivity::class.java))}
            //country ->
            //intent.putExtra("title", country.countryName)
            //startActivity(intent)

        }
        mypage_rv.adapter = mAdapter

        //리사이클러뷰 레이아웃매니저
        val lm = LinearLayoutManager(this)
        mypage_rv.layoutManager = lm
        mypage_rv.setHasFixedSize(true)
    }
}