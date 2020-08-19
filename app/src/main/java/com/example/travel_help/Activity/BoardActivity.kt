package com.example.travel_help.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.travel_help.RecyclerViewAdapter.BoardRvAdapter
import com.example.travel_help.DataClass.DataClassPost
import com.example.travel_help.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.board.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class BoardActivity : AppCompatActivity() {
    private val REQUEST_WRITE = 3000
    private val REQUEST_READ = 1000
    private var position = -1
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


        request()

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
            startActivityForResult(intent,REQUEST_WRITE)
        })

        //리사이클러뷰 어댑터
        val intent = Intent(this, PostReadActivity::class.java)
        val mAdapter = BoardRvAdapter(this, dummy){
                post, position -> intent.putExtra("title",post.title)
            intent.putExtra("date",post.date)
            intent.putExtra("airport",post.airport)
            intent.putExtra("content",post.content)

            this.position = position

            startActivityForResult(intent,REQUEST_READ)
            //startActivity(intent)
        }
        board_rv.adapter=mAdapter
    }

    fun request(){    //게시판 이름받아야
        val url = "http://172.30.1.34:3000/board"

        try {
            val requestQueue = Volley.newRequestQueue(this)
            val jsonArrayRequest = JsonArrayRequest(
                Request.Method.GET, url, null,

                Response.Listener { response ->
                    try {
                        Log.d("---------------------","게시판 불러오기 성공")

                        val jsonArray = JSONArray(response.toString())//JSONObject(response.toString())

//                        for(i in 0..jsonArray.length()-1){
//                            Log.d("---------------------","1")
//                            val jsonObject = jsonArray.getJSONObject(i)
//                            var title = jsonObject.getString("title")
//                            var content = jsonObject.getString("content")
//                            //게시판엔 날짜랑 공항정보 필요없으니까
//                            dummy.add(DataClassPost(title, 0, "",content))
//                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },

                Response.ErrorListener { error ->
                    Log.d("---------------------","err")
                    error.printStackTrace()
                })

            jsonArrayRequest.retryPolicy = DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            requestQueue.add(jsonArrayRequest)
            //
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==RESULT_OK){
            when (requestCode) {
                //게시글 작성시 rv에 반영
                REQUEST_WRITE -> {
                    val newpost = getPostData(data)
                    dummy.add(newpost)
                    board_rv.adapter?.notifyDataSetChanged()
                }
                //게시글 수정/삭제시 rv에 반영
                REQUEST_READ->{
                    var post_state = data?.getStringExtra("isChanged")
                    val changedpost = getPostData(data)
                    if(post_state=="changed"){
                        //position값 얻기가 어려워서 게시글제목으로 해당 게시글 수정하게함
                        for((i,post) in dummy.withIndex()){
                            dummy[position]=changedpost
                        }
                    }else if(post_state=="deleted"){
                        dummy.removeAt(position)
                    }
                    board_rv.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    fun getPostData(intent:Intent?):DataClassPost{
        val title = intent?.getStringExtra("title")
        val date = intent?.getIntExtra("date", -3)!!
        val airport = intent?.getStringExtra("airport")
        val content = intent?.getStringExtra("content")
        return DataClassPost(title, date, airport, content)
    }
}