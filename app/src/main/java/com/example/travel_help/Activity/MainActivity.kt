package com.example.travel_help.Activity

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travel_help.DataClass.DataClassCountry
import com.example.travel_help.RecyclerViewAdapter.MainRvAdapter
import com.example.travel_help.R
import kotlinx.android.synthetic.main.main.*


class MainActivity : AppCompatActivity() {

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
    val countryList = arrayListOf<DataClassCountry>(
        DataClassCountry("German"),
        DataClassCountry("France")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        val navView: BottomNavigationView = findViewById(R.id.board_nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)


        //리사이클러뷰 어댑터
        val intent = Intent(this, BoardActivity::class.java)
        val mAdapter = MainRvAdapter(this, countryList) {
            //country ->startActivity(intent)}//(Intent(this, BoardActivity::class.java))}
                country ->
            intent.putExtra("title", country.countryName)
            startActivity(intent)
        }
        main_rv.adapter = mAdapter

        //리사이클러뷰 레이아웃매니저
        val lm = LinearLayoutManager(this)
        main_rv.layoutManager = lm
        main_rv.setHasFixedSize(true)

        //임시 버튼 연결
        /*
        var boardCountry: String = "나라" //intent로 넘겨서 게시판종류 정할때 사용
        main_btn_board_Deutsch.setOnClickListener{
            val intent = Intent(this, BoardActivity::class.java)
            boardCountry="@string/Deutsch"
        }
        main_btn_board_France.setOnClickListener{
            boardCountry="@string/France"
        }
        */




    }
}
