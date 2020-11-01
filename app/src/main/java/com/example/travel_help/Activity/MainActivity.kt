package com.example.travel_help.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travel_help.App
import com.example.travel_help.DataClass.DataClassCountry
import com.example.travel_help.DataClass.DataClassSchedule
import com.example.travel_help.RecyclerViewAdapter.MainRvAdapter
import com.example.travel_help.R
import com.example.travel_help.RecyclerViewAdapter.ScheduleRvAdapter
import kotlinx.android.synthetic.main.board.*
import kotlinx.android.synthetic.main.main.*


class MainActivity : AppCompatActivity() {
    private val REQUEST = 1001
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.navigation_chatting -> {
                val intent = Intent(this, MsgListActivity::class.java)
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

    val scheduleLIst = arrayListOf<DataClassSchedule>(
        DataClassSchedule("프랑스", "20200919~20200926"),
        DataClassSchedule("독일", "20200926~20201006"),
        DataClassSchedule("벨기에", "20201007~20201010")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        val navView: BottomNavigationView = findViewById(R.id.navigation)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val user_id = App.prefs.getString("id","fail")
        tv_name.setText(user_id+"님\n즐거운 여행되세요")

//        //일정관리버튼
//        btn_calendar.setOnClickListener(View.OnClickListener {
//            val intent = Intent(this, CalendarActivity::class.java)
//            startActivity(intent)
//        })
//
//        //마이페이지 버튼
//        btn_setting.setOnClickListener(View.OnClickListener {
//            val intent = Intent(this, MypageActivity::class.java)
//            startActivity(intent)
//        })




        //스케쥴 리스트 마지막에 빈
        if(scheduleLIst.last().city=="추가"){
        }else{
            scheduleLIst.add(DataClassSchedule("추가", null))
        }

        // 스케쥴 리사이클러뷰 어댑터
        val s_mAdapter = ScheduleRvAdapter(this, scheduleLIst) {
            //country ->startActivity(intent)}//(Intent(this, BoardActivity::class.java))}
                country, position ->
            if(country.city!="추가"){
                val s_intent = Intent(this, BoardActivity::class.java)
                s_intent.putExtra("title", country.city)
                startActivity(s_intent)
            }else{
                val s_intent = Intent(this, CountryActivity::class.java)
                startActivityForResult(s_intent,REQUEST)
            }
        }
        rv_schedule.adapter = s_mAdapter

        //스케쥴 리사이클러뷰 레이아웃매니저
        val s_lm = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_schedule.layoutManager = s_lm
        rv_schedule.setHasFixedSize(true)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RESULT_OK){
            rv_schedule.adapter?.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
