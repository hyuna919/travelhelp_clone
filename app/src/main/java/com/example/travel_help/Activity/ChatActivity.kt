package com.example.travel_help.Activity

import android.app.Activity
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

    private var mSocket: Socket = IO.socket("http://172.30.1.1:3000/")


    var dummy = arrayListOf<DataClassChatting>(
        DataClassChatting("abc_root","abc","root","2020-09-15", "01:12","안녕하세요")
    )
    val mAdapter = ChatRvAdapter(this, dummy){}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat)

        //유저 아이디
        val id = App.prefs.getString("id","fail")
        //채팅방이름="처음보낸사람_받는사람"
        var roomNumber = "abc_root"

        rv_chat.adapter = mAdapter
        //리사이클러뷰 레이아웃매니저
        val lm = LinearLayoutManager(this)
        rv_chat.layoutManager = lm
        rv_chat.setHasFixedSize(true)

        imageButton.setOnClickListener(View.OnClickListener {
            sendMessage()
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
                userId.put("username", id)
                userId.put("roomNumber", roomNumber)
                Log.e("id:",id)
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

    internal var onNewUser: Emitter.Listener = Emitter.Listener { args ->
        runOnUiThread(Runnable {
//            val length = args.size
//
//            if (length == 0) {
//                return@Runnable
//            }
            //Here i'm getting weird error..................///////run :1 and run: 0
            var username = args[0].toString()
            Log.d("------------",username)
            try {
                val `object` = JSONObject(username)
                username = `object`.getString("username")
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        })
    }

    fun sendMessage() {
        val now = System.currentTimeMillis()
        //나중에 바꿔줄것 밑의 yyyy-MM-dd는 그냥 20xx년 xx월 xx일만 나오게 하는 식
        val sdf_date = SimpleDateFormat("yyyy-MM-dd")
        val sdf_time = SimpleDateFormat("kk:mm")

        val roomNumber = "abc_root"
        val sender = App.prefs.getString("id","fault")
        val receiver = "abc"
        val date = sdf_date.format(Date(now))
        val time = sdf_time.format(Date(now))
        val message = "렘브란트와 표정있는 얼굴들"

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