package com.example.travel_help.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travel_help.DataClass.DataClassMsg
import com.example.travel_help.RecyclerViewAdapter.MsgListAdapter
import com.example.travel_help.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.msg_list.*


class MsgListActivity : AppCompatActivity() {
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
    val msgList = arrayListOf<DataClassMsg>(
        DataClassMsg("guest", "안녕하세요"),
        DataClassMsg("Franfran", "hello:)")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.msg_list)
        val navView: BottomNavigationView = findViewById(R.id.board_nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)


        //리사이클러뷰 어댑터
        val intent =Intent(this, ChatActivity::class.java)
        val mAdapter = MsgListAdapter(this, msgList) {
            chatting -> intent.putExtra("chat_other",chatting.name)
            startActivity(intent)
        }
        msg_list_rv.adapter = mAdapter

        //리사이클러뷰 레이아웃매니저
        val lm = LinearLayoutManager(this)
        msg_list_rv.layoutManager = lm
        msg_list_rv.setHasFixedSize(true)


//        //임시 버튼
//        button.setOnClickListener(View.OnClickListener {
//            val intent =Intent(this, ChatActivity::class.java)
//            startActivity(intent)
//        })
    }
}