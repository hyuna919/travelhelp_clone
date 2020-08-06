package com.example.travel_help.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.travel_help.R
import kotlinx.android.synthetic.main.login.*
import org.json.JSONException
import org.json.JSONObject

class LoginActivity :AppCompatActivity(){
    private val SIGNUP_CODE = 100

    var accounts = mutableMapOf<String, String>(
        "root" to "1234",
        "guest" to "5678"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)


        //로그인 버튼
        bt_login.setOnClickListener(View.OnClickListener {
            var u_id = put_id.text.toString()
            var u_pw = put_pw.text.toString()

            for((id,pw) in accounts){
                if((u_id==id)&&(u_pw==pw)){
                    var intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("id",id)
                    intent.putExtra("pw",pw)
                    request()
                    startActivity(intent)
                    finish()
                }
            }

        })

        //회원가입 버튼
        bt_signup.setOnClickListener(View.OnClickListener {
            var keys = accounts.keys.toTypedArray()
            var intent = Intent(this, SignupActivity::class.java)
            intent.putExtra("id",keys)
            startActivityForResult(intent,SIGNUP_CODE)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data!!)
        if(resultCode==RESULT_OK){
            when (requestCode) {
                SIGNUP_CODE -> {
                    val id = data.getStringExtra("id")
                    val pw = data.getStringExtra("pw")
                    accounts.putIfAbsent(id,pw)
                }
            }
        }else{
            return
        }
    }

    fun request() {
        val url = "http://172.30.1.44:3000"

        val testjson = JSONObject()
        try {
            testjson.put("id", put_id.text.toString())
            testjson.put("password", put_pw.text.toString())
            val jsonString = testjson.toString() //완성된 json 포맷

            val requestQueue = Volley.newRequestQueue(this)
            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, testjson,
                Response.Listener { response ->
                    try {
                        Log.d("---------------------","데이터전송 성공")

                        val jsonObject = JSONObject(response.toString())

                        val resultId = jsonObject.getString("approve_id")
                        val resultPassword = jsonObject.getString("approve_pw")

                        if ((resultId == "OK") and (resultPassword == "OK")) {
                            //로그인 성공
                        } else {
                            //로그인 실패
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