package com.example.travel_help.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travel_help.RecyclerViewAdapter.BoardRvAdapter
import com.example.travel_help.DataClass.DataClassBoard
import com.example.travel_help.DataClass.DataClassPost
import com.example.travel_help.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.board.*


class BoardActivity : AppCompatActivity() {
    private lateinit var textMessage: TextView
    private val POST_REQUEST = 3000
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
    var dummy = arrayListOf<DataClassPost>(
        DataClassPost("제목333",20200202,"Paris","aaaaaaaaaaaaaaaaaaaaa"),
        DataClassPost("제목444",10101010,"Tokyo","bbbbbbbbbbbbb")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.board)
        val navView: BottomNavigationView = findViewById(R.id.board_nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        //리사이클러뷰 레이아웃매니저
        val lm = LinearLayoutManager(this)
        board_rv.layoutManager = lm
        board_rv.setHasFixedSize(true)

        //게시판 이름
        val title = intent.getStringExtra("title")
        board_boardtitle.setText(title)

        //글 작성 버튼
        board_btn_write.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, PostWriteActivity::class.java)
            startActivityForResult(intent,POST_REQUEST)
        })

        //리사이클러뷰 어댑터
        board_rv.adapter = BoardRvAdapter(this, dummy)



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==RESULT_OK){
            when (requestCode) {
                POST_REQUEST -> {
                    val title = data?.getStringExtra("title")
                    val date = data?.getIntExtra("date", -3)!!
                    val airport = data?.getStringExtra("airport")
                    val content = data?.getStringExtra("content")
                    dummy.add(DataClassPost(title, date, airport, content))

                    board_rv.adapter?.notifyDataSetChanged()
                }
            }
        }else{
            return
        }
    }




}