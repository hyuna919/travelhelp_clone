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
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.suspendCoroutine


class BoardActivity : AppCompatActivity(), CoroutineScope {
    private val REQUEST_WRITE = 3000
    private val REQUEST_READ = 1000
    var position = -1
    private lateinit var mJob: Job

    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.board)
        board_rv.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        val navView: BottomNavigationView = findViewById(R.id.board_nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        //게시판 이름
        val title = intent.getStringExtra("title")
        board_boardtitle.setText(title)

        //당겨서 새로고침
        board_swipe.setOnRefreshListener{
            //coroutine()
            board_swipe.isRefreshing = false
        }

        //새로고침 버튼
        board_btn_refresh.setOnClickListener(View.OnClickListener{
            //coroutine()
        })

        //글 작성 버튼
        board_btn_write.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, PostWriteActivity::class.java)
            startActivityForResult(intent,REQUEST_WRITE)
        })

        //리사이클러뷰 레이아웃매니저
        val lm = LinearLayoutManager(this)
        board_rv.layoutManager = lm
        board_rv.setHasFixedSize(true)
        board_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastPosition = (board_rv.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val totalCount = board_rv.adapter!!.itemCount
                Log.d("------lastPosition---",lastPosition.toString())
                Log.d("------totalCount---",totalCount.toString())

                if (lastPosition+1 == totalCount) {
                    coroutine(lastPosition)
                }
            }
        })
        coroutine(-1)

    }

    //리사이클러뷰 어댑터
    fun binding(list:ArrayList<DataClassPost>){
        val intent = Intent(this, PostReadActivity::class.java)
        val mAdapter = BoardRvAdapter(this@BoardActivity, list){
                post, position -> intent.putExtra("post_id",post.post_id)
                intent.putExtra("title",post.title)
                intent.putExtra("date",post.date)
                intent.putExtra("airport",post.airport)
                intent.putExtra("content",post.content)
                intent.putExtra("writer_id",post.content)
                intent.putExtra("createdAt:",post.content)

            //this.position = position

            startActivityForResult(intent,REQUEST_READ)
            //startActivity(intent)
        }
        board_rv.adapter=mAdapter
    }

    fun coroutine(key:Int){
        val scope = CoroutineScope(Dispatchers.Main)
        var list = ArrayList<DataClassPost>()
        //var a = key
        scope.launch(Dispatchers.IO) {
            async(coroutineContext){
                list=request(key)
            }.await()
            binding(list)
        }
    }

    private suspend fun request(key:Int) = suspendCoroutine<ArrayList<DataClassPost>>{
    //private fun request():ArrayList<DataClassPost>{
        val url = "http://172.30.1.34:3000/board/$key/10"
        var list = ArrayList<DataClassPost>()
        try {
            val requestQueue = Volley.newRequestQueue(this@BoardActivity)
            val jsonArrayRequest = JsonArrayRequest(
                Request.Method.GET, url, null,

                Response.Listener { response ->
                    try {
                        val jsonArray = JSONArray(response.toString())//JSONObject(response.toString())

                        for(i in 0..jsonArray.length()-1){
                            val jsonObject = jsonArray.getJSONObject(i)
                            var id = jsonObject.getString("id")
                            var title = jsonObject.getString("title")
                            var date = jsonObject.getString("date")
                            var airport = jsonObject.getString("airport")
                            var content = jsonObject.getString("content")
                            var writer_id = jsonObject.getString("airport")
                            var createdAt = jsonObject.getString("createdAt")
                            list.add(DataClassPost(id, title, date, airport,content,writer_id,createdAt))
                        }
                        it.resumeWith(Result.success(list))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },

                Response.ErrorListener { error ->
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
            Log.d("에러에러에러","확인")
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==RESULT_OK){
            when (requestCode) {
                //게시글 작성시 rv에 반영
                REQUEST_WRITE -> {
                    //val newpost = getPostData(data)
                    //dummy.add(newpost)
                    board_rv.adapter?.notifyDataSetChanged()
                }
                //게시글 수정/삭제시 rv에 반영
               REQUEST_READ->{
                   board_rv.adapter?.notifyDataSetChanged()
//                    var post_state = data?.getStringExtra("isChanged")
//                    val changedpost = getPostData(data)
//                    if(post_state=="changed"){
//                        //position값 얻기가 어려워서 게시글제목으로 해당 게시글 수정하게함
//                        for((i,post) in dummy.withIndex()){
//                            dummy[position]=changedpost
//                        }
//                    }else if(post_state=="deleted"){
//                        dummy.removeAt(position)
//                    }
//                    board_rv.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

//    fun getPostData(intent:Intent?):DataClassPost{
//        val title = intent?.getStringExtra("title")
//        val date = intent?.getIntExtra("date", -3)!!
//        val airport = intent?.getStringExtra("airport")
//        val content = intent?.getStringExtra("content")
//        return DataClassPost(title, date, airport, content)
//    }


    override fun onDestroy() {
        super.onDestroy()
        mJob.cancel()
    }
}