package com.example.travel_help.Activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
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
import com.example.travel_help.DataClass.DataClassPost
import com.example.travel_help.R
import kotlinx.android.synthetic.main.post_read.*
import kotlinx.android.synthetic.main.post_write.*
import org.json.JSONException
import org.json.JSONObject
import java.time.Year
import java.util.*

class PostWriteActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_write)

        //기존 게시글 수정이라면
        val change = intent.getBooleanExtra("change",false)
        if(change==true){
            val title = intent.getStringExtra("title")
            val date = intent.getIntExtra("date",-1)
            val airport = intent.getStringExtra("airport")
            val content = intent.getStringExtra("content")

            pwr_title.setText(title)
            //게시글 수정시 제목수정 불가
            pwr_title.isFocusable=false
            pwr_title.isFocusableInTouchMode=false
            pwr_date?.setText(date.toString())
            pwr_airport?.setText(airport)
            pwr_content?.setText(content)
        }

        //날짜 입력
        pwr_date.setOnClickListener{View->
            var calendar= Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var day = calendar.get(Calendar.DAY_OF_MONTH)



            var c_listener = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                    pwr_tv_date?.setText(p1.toString()+(p2+1).toString()+p3.toString())
                }
            }

            var dialog = DatePickerDialog(this,c_listener,year,month,day)
            dialog.show()
        }

        //저장 버튼(pwr_btn)
        pwr_btn.setOnClickListener(View.OnClickListener {
            //setPost()
            lateinit var intent:Intent
            if(change==true){
                intent = Intent(this,PostReadActivity::class.java)
            }else{
                intent = Intent(this,BoardActivity::class.java)
            }

            val title = pwr_title.text.toString()
            val date = pwr_tv_date.text.toString().toInt()
            val airport = pwr_airport.text.toString()
            val content = pwr_content.text.toString()

            request(intent, title, date, airport, content)

            //setResult(RESULT_OK,intent)

            //finish()
        })

    }

    fun request(intent:Intent, title:String, date:Int, airport:String, content:String) {
        val url = "http://172.30.1.34:3000/posts/writePost"


        val testjson = JSONObject()
        try {
            testjson.put("id", "root")//세션 만들기 전이라 임시로
            testjson.put("title", title)
            testjson.put("date", date)
            testjson.put("airport", airport)
            testjson.put("content", content)
            val jsonString = testjson.toString()

            val requestQueue = Volley.newRequestQueue(this)
            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, testjson,
                Response.Listener { response ->
                    try {
                        Log.d("---------------------","게시글전송 성공")

                        val jsonObject = JSONObject(response.toString())

                        val result = jsonObject.getString("approve")


                        if(result == "OK"){
                            startActivity(intent)
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