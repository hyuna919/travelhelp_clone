package com.example.travel_help.Activity

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.travel_help.App
import com.example.travel_help.R
import kotlinx.android.synthetic.main.login.*
import org.json.JSONException
import org.json.JSONObject

class LoginActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        //뒤로 버튼
        btn_back.setOnClickListener(View.OnClickListener {
            finish()
        })

        //로그인 버튼
        bt_login.setOnClickListener(View.OnClickListener {
            var u_id = put_id.text.toString()
            var u_pw = put_pw.text.toString()

            request(u_id, u_pw)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun request(id:String, pw:String) {
        val url = "http://172.30.1.51:3000/users/login"

        val testjson = JSONObject()
        try {
            testjson.put("id", id)
            testjson.put("password", pw)

            val jsonString = testjson.toString()


            val requestQueue = Volley.newRequestQueue(this)
            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, testjson,
                Response.Listener { response ->
                    try {

                        val jsonObject = JSONObject(response.toString())

                        App.prefs.setString("token",jsonObject.getString("token"))
                        App.prefs.setString("id",jsonObject.getString("id"))

                        val a = App.prefs.getString("token","aa")

                        if(a != null){
                            var intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("id", id)
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