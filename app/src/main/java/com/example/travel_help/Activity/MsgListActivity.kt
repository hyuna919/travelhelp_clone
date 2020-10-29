package com.example.travel_help.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.travel_help.App
import com.example.travel_help.DataClass.DataClassMsg
import com.example.travel_help.DataClass.DataClassPost
import com.example.travel_help.RecyclerViewAdapter.MsgListAdapter
import com.example.travel_help.R
import com.example.travel_help.RecyclerViewAdapter.BoardRvAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.msg_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.suspendCoroutine


class MsgListActivity : AppCompatActivity(), CoroutineScope {
    var list = ArrayList<DataClassMsg>()
    private lateinit var mAdapter: MsgListAdapter

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
        setContentView(R.layout.msg_list)
        val navView: BottomNavigationView = findViewById(R.id.board_nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        msg_list_rv.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))


        //리사이클러뷰 어댑터
        val intent =Intent(this, ChatActivity::class.java)
        mAdapter = MsgListAdapter(this) {
            chatting -> intent.putExtra("other_id",chatting.name)
            intent.putExtra("roomNumber",chatting.roomNumber)
            startActivity(intent)
        }
        msg_list_rv.adapter = mAdapter

        //리사이클러뷰 레이아웃매니저
        val lm = LinearLayoutManager(this)
        msg_list_rv.layoutManager = lm
        msg_list_rv.setHasFixedSize(true)

        coroutine(-1)
    }

    fun binding(list:ArrayList<DataClassMsg>){
        mAdapter.setMsgs(list)
    }

    fun coroutine(key:Int){
        val scope = CoroutineScope(Dispatchers.Main)

        scope.launch(Dispatchers.Main) {
            async(coroutineContext){
                if(list != null){
                    list.addAll(request(key))
                }else{
                    list=request(key)
                }

            }.await()
            binding(list)
        }
    }




    private suspend fun request(key:Int) = suspendCoroutine<ArrayList<DataClassMsg>>{
        //private fun request():ArrayList<DataClassMsg>{
        val url = "http://172.30.1.34:3000/messages/msglist"
        var list = ArrayList<DataClassMsg>()
        try {
            val requestQueue = Volley.newRequestQueue(this)
            val jsonArrayRequest = JsonArrayRequest(
                Request.Method.GET, url, null,

                Response.Listener { response ->
                    try {
                        val jsonArray = JSONArray(response.toString())//JSONObject(response.toString())

                        for(i in 0..jsonArray.length()-1){
                            val jsonObject = response.getJSONObject(i)
                            var roomNumber = jsonObject.getString("roomNumber")
                            var sender = jsonObject.getString("sender")
                            var receiver = jsonObject.getString("receiver")
                            var contents = jsonObject.getString("message")

                            val user_id = App.prefs.getString("token","fail")
                            if(sender == user_id){
                                list.add(DataClassMsg(roomNumber, receiver, contents))
                            }else if(receiver == user_id){
                                list.add(DataClassMsg(roomNumber, sender, contents))
                            }
                            list.add(DataClassMsg(roomNumber, sender, contents))

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

    override val coroutineContext: CoroutineContext
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
}