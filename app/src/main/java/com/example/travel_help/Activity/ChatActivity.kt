package com.example.travel_help.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travel_help.App
import com.example.travel_help.DataClass.DataClassChatting
import com.example.travel_help.R
import com.example.travel_help.RecyclerViewAdapter.ChatRvAdapter
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import kotlinx.android.synthetic.main.chat.*
import kotlinx.android.synthetic.main.chat.btn_back
import kotlinx.android.synthetic.main.login.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity: Activity() {
    //private lateinit var chating_Text: EditText
    //private lateinit var chat_Send_Button: Button

    private var hasConnection: Boolean = false
    private var thread2: Thread? = null
    private var startTyping = false
    private var time = 2

    private var mSocket: Socket = IO.socket("http://172.30.1.34:3000/")


    val mAdapter = ChatRvAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat)

        //채팅방
        val user_id = App.prefs.getString("id","fail")
        val other_id = intent?.getStringExtra("other_id")
        val roomNumber = intent?.getStringExtra("roomNumber")

        board_boardtitle.setText(other_id)

        //뒤로 버튼
        btn_back.setOnClickListener(View.OnClickListener {
            finish()
        })

        //

        rv_chat.adapter = mAdapter
        //리사이클러뷰 레이아웃매니저
        val lm = LinearLayoutManager(this)
        rv_chat.layoutManager = lm
        rv_chat.setHasFixedSize(true)

        imageButton.setOnClickListener(View.OnClickListener {
            sendMessage(intent)
        })

        if (savedInstanceState != null) {
            hasConnection = savedInstanceState.getBoolean("hasConnection")
        }

        if (hasConnection) {

        } else {
            //소켓연결
            try{
                mSocket.connect()
            }catch (e:java.lang.Exception){
                Log.d(".......",e.toString())
            }

            //이벤트 송신처리
            //이름,룸네임을 jsonObject에 담음
            val userId = JSONObject()
            try {
                userId.put("username", user_id)
                userId.put("roomNumber", roomNumber)
                Log.e("id:",user_id)
                //socket.emit은 메세지 전송임
                mSocket.emit("connect user", userId)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            //이벤트 수신처리
            mSocket.on("connect user", onNewUser)
            mSocket.on("chat message", onNewMessage)
        }

        hasConnection = true
    }

    internal var onNewMessage: Emitter.Listener = Emitter.Listener { args ->
        runOnUiThread(Runnable {
            val data = args[0] as JSONObject
            val roomNumber: String
            val sender: String
            val receiver: String
            val date: String
            val time: String
            val message: String
            try {

                roomNumber = "abc_root"
                sender = data.getString("sender")
                receiver = data.getString("receiver")
                date = data.getString("date")
                time = data.getString("time")
                message = data.getString("message")

                val format = DataClassChatting(roomNumber,sender, receiver, date, time, message)
                mAdapter.addItem(format)
                mAdapter.notifyDataSetChanged()
            } catch (e: Exception) {
                return@Runnable
            }
        })
    }

    internal var onNewUser: Emitter.Listener = Emitter.Listener { args->
        runOnUiThread(Runnable {
            val datalist = args[0] as JSONArray
            var roomNumber: String
            var sender: String
            var receiver: String
            var date: String
            var time: String
            var message: String
            try {
                for(i in 0..datalist.length()-1) {
                    var data = datalist[i] as JSONObject
                    roomNumber = "abc_root"
                    sender = data.getString("sender")
                    receiver = data.getString("receiver")
                    date = data.getString("date")
                    time = data.getString("time")
                    message = data.getString("message")


                    val format = DataClassChatting(roomNumber, sender, receiver, date, time, message)
                    mAdapter.addItem(format)
                    mAdapter.notifyDataSetChanged()
                }

            } catch (e: JSONException) {
                e.printStackTrace()
                return@Runnable
            }

        })
    }

    fun sendMessage(intent: Intent) {
//        val now = System.currentTimeMillis()
        val now = Calendar.getInstance().getTime()
        //나중에 바꿔줄것 밑의 yyyy-MM-dd는 그냥 20xx년 xx월 xx일만 나오게 하는 식
        val sdf_date = SimpleDateFormat("yyyy-MM-dd")
        val sdf_time = SimpleDateFormat("kk:mm")

        val roomNumber = intent.getStringExtra("roomNumber")
        val sender = App.prefs.getString("id","fault")
        val receiver = "abc"
        val date = sdf_date.format(now.getTime())
        val time = sdf_time.format(now.getTime())
        val message = editText.text.toString()

        if (TextUtils.isEmpty(message)) {
            return
        }

        editText.setText("")

        val jsonObject = JSONObject()
        try {
            jsonObject.put("roomNumber", roomNumber)
            jsonObject.put("sender", sender)
            jsonObject.put("receiver", receiver)
            jsonObject.put("date", date)
            jsonObject.put("time", time)
            jsonObject.put("message", message)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        mSocket.emit("chat message", jsonObject)
    }
}