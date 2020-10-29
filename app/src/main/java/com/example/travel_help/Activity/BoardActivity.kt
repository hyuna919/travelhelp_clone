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


class BoardActivity : AppCompatActivity(), CoroutineScope {
    private val REQUEST_WRITE = 3000
    private val REQUEST_READ = 1000
    var position = -1
    private var mJob= Job()
    var list = ArrayList<DataClassPost>()
    private lateinit var mAdapter: BoardRvAdapter
    var search = false
    private var board_title :String=""

    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

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
                val intent = Intent(this, NotiActivity::class.java)
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

        val navView: BottomNavigationView = findViewById(R.id.navigation)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        //게시판 이름
        board_title = intent.getStringExtra("title")
        tv_boardtitle.setText(board_title)
        when(intent.getStringExtra("title")){
            "독일" -> board_title = "Germany"
            "프랑스" -> board_title = "France"
            "영국" -> board_title = "UK"
            "벨기에" -> board_title = "Belgium"
            "터키" -> board_title = "Turkey"
        }

        //당겨서 새로고침
        board_swipe.setOnRefreshListener{
            //coroutine()
            board_swipe.isRefreshing = false
            board_rv.adapter?.notifyDataSetChanged()
        }

        //뒤로 버튼
        btn_back.setOnClickListener(View.OnClickListener {
            finish()
        })

        //검색 버튼
        btn_search.setOnClickListener(View.OnClickListener {
            tv_boardtitle.setVisibility(View.INVISIBLE)
            et_search.setVisibility(View.VISIBLE)
        })

        //글 작성 버튼
        btn_write.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, PostWriteActivity::class.java)
            intent.putExtra("country",board_title)
            startActivityForResult(intent,REQUEST_WRITE)
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

            startActivityForResult(intent,REQUEST_READ)
        }
        board_rv.adapter=mAdapter

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

    fun coroutine(key:Int){
        val scope = CoroutineScope(Dispatchers.Main)

        scope.launch(Dispatchers.Main) {
            async(coroutineContext){
                if(list != null){
                    list.addAll(request(board_title,key))
                }else{
                    list=request(board_title,key)
                }

            }.await()
            binding(list)
        }
    }

    //리사이클러뷰 어댑터
    fun binding(list:ArrayList<DataClassPost>){
        mAdapter.setPosts(list)

    }




    private suspend fun request(country:String,key:Int) = suspendCoroutine<ArrayList<DataClassPost>>{
    //private fun request():ArrayList<DataClassPost>{
        val url = "http://172.30.1.34:3000/board/$country/$key/10"
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
                            var post_id = jsonObject.getString("id")
                            var type = jsonObject.getString("type")
                            var title = jsonObject.getString("title")
                            var content = jsonObject.getString("content")
                            var writer_id = jsonObject.getString("writer_id")
                            var createdAt = jsonObject.getString("createdAt")
                            var country =  jsonObject.getString("country")
                            var latitude = 0
//                            if(jsonObject.getString("latitude")!=null){
//                                latitude = jsonObject.getString("latitude")
//                            }
                            var longitude = 0
//                            if(jsonObject.getString("longitude")!=null){
//                                longitude = jsonObject.getString("longitude")
//                            }

                            var image =  null
                            var recommended =  jsonObject.getInt("recommended")
                            list.add(DataClassPost(post_id, type, title, content,writer_id,createdAt, country, latitude, longitude, image, recommended))
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