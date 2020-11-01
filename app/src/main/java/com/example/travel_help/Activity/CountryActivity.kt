package com.example.travel_help.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.travel_help.RecyclerViewAdapter.BoardRvAdapter
import com.example.travel_help.DataClass.DataClassPost
import com.example.travel_help.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.board.*
import kotlinx.android.synthetic.main.board.btn_back
import kotlinx.android.synthetic.main.board_rv.*
import kotlinx.android.synthetic.main.login.*
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.suspendCoroutine


class CountryActivity : AppCompatActivity() {
    private lateinit var mAdapter: BoardRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.country)
        board_rv.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))


        //뒤로 버튼
        btn_back.setOnClickListener(View.OnClickListener {
            finish()
        })



        //리사이클러뷰 어댑터
        val intent = Intent(this, PostReadActivity::class.java)
        mAdapter = BoardRvAdapter(){ post, position -> intent.putExtra("post_id",post.post_id)
            intent.putExtra("type",post.type)
            intent.putExtra("title",post.title)
            intent.putExtra("content",post.content)
            intent.putExtra("writer_id",post.writer_id)
            intent.putExtra("createdAt:",post.createdAt)
            intent.putExtra("city",title)

        }
        board_rv.adapter=mAdapter

        //리사이클러뷰 레이아웃매니저
        val lm = LinearLayoutManager(this)
        board_rv.layoutManager = lm
        board_rv.setHasFixedSize(true)

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}