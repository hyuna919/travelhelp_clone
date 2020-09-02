package com.example.travel_help.Activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.travel_help.App
import com.example.travel_help.R
import kotlinx.android.synthetic.main.post_write.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class PostWriteActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_write)
        var post_id:String="0"

        //기존 게시글 수정이라면
        val change = intent.getBooleanExtra("change",false)
        if(change==true){
            post_id = intent.getStringExtra("post_id")
            val title = intent.getStringExtra("title")
            val date = intent.getStringExtra("date")
            val airport = intent.getStringExtra("airport")
            val content = intent.getStringExtra("content")

            pwr_title.setText(title)
            //게시글 수정시 제목수정 불가
            pwr_title.isFocusable=false
            pwr_title.isFocusableInTouchMode=false
            pwr_tv_date?.setText(date)
            pwr_airport?.setText(airport)
            pwr_content?.setText(content)
        }

        //날짜 입력
        pwr_btn_date.setOnClickListener{ View->
            var calendar= Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var day = calendar.get(Calendar.DAY_OF_MONTH)


            var c_listener = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                    pwr_tv_date?.setText(p1.toString()+"-"+(p2+1).toString()+"-"+p3.toString())
                }
            }

            var dialog = DatePickerDialog(this,c_listener,year,month,day)
            dialog.show()
        }

        //저장 버튼(pwr_btn)
        pwr_btn.setOnClickListener(View.OnClickListener {
            lateinit var intent:Intent
            val title = pwr_title.text.toString()
            val date = pwr_tv_date.text.toString()
            val airport = pwr_airport.text.toString()
            val content = pwr_content.text.toString()

            if(change==true){   //게시글 수정이라면
                request(title, date, airport, content,post_id)
            }else{      //게시글 등록(수정x)라면
                request(title, date, airport, content,post_id)
            }
        })

    }

    fun request(title:String, date:String, airport:String, content:String, post_id:String) {    //post_id 0:등록, 0이 아니면:수정
        lateinit var url:String
        if(post_id=="0"){   //등록
            url = "http://172.30.1.34:3000/posts/writePost"
        }else{              //수정
            url = "http://172.30.1.34:3000/posts/updatePost"
        }


        val testjson = JSONObject()
        try {
            val tmp = App.prefs.getString("token","fail")
            testjson.put("accessToken", tmp)
            testjson.put("title", title)
            testjson.put("date", date)
            testjson.put("airport", airport)
            testjson.put("content", content)
            if(post_id != "0") {
                Log.d("++++++++++++++++",post_id)
                testjson.put("post_id", post_id)
            }



            val requestQueue = Volley.newRequestQueue(this)
            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, testjson,
                Response.Listener { response ->
                    try {
                        Log.d("---------------------","게시글전송 성공")

                        val jsonObject = JSONObject(response.toString())
                        val result = jsonObject.getString("response")

                        if(result == "OK"){
                            finish()
                            Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },

                Response.ErrorListener { error ->
                    error.printStackTrace()
                })

            jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            requestQueue.add(jsonObjectRequest)
            //
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }


}