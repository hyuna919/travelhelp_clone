package com.example.travel_help.Activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.travel_help.App
import com.example.travel_help.R
import kotlinx.android.synthetic.main.board_rv.*
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
            val location = intent.getStringExtra("location")
            val content = intent.getStringExtra("content")

            ed_title.setText(title)
            //게시글 수정시 제목수정 불가
            ed_title.isFocusable=false
            ed_title.isFocusableInTouchMode=false
            tv_location?.setText(location)
            ed_contents?.setText(content)
        }

        val models = resources.getStringArray(R.array.post_type)
        val adapter = ArrayAdapter(baseContext, R.layout.support_simple_spinner_dropdown_item, models)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinner.adapter = adapter
        adapter.notifyDataSetChanged();
        spinner.setSelection(0)

        //저장 버튼(pwr_btn)
        btn_push.setOnClickListener(View.OnClickListener {
            val type="["+spinner.selectedItem.toString()+"]"
            val title = ed_title.text.toString()
            val location = tv_location.text.toString()
            val content = ed_contents.text.toString()
            val country = intent.getStringExtra("country")

            if(change==true){   //게시글 수정이라면
                request(type,title, location, content,post_id ,country)
            }else{      //게시글 등록(수정x)라면
                request(type,title, location, content,post_id ,country)
            }
        })

    }

    fun request(type:String, title:String, location:String, content:String, post_id:String ,city:String) {    //post_id 0:등록, 0이 아니면:수정
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
            testjson.put("type", type)
            testjson.put("title", title)
            testjson.put("location", location)
            testjson.put("content", content)
            testjson.put("recommended",0)
            testjson.put("country",city)
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