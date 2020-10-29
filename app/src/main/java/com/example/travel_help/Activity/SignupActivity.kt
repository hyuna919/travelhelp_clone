package com.example.travel_help.Activity

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.travel_help.R
import kotlinx.android.synthetic.main.signup.*
import kotlinx.coroutines.*
import org.json.JSONException
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext


class SignupActivity : AppCompatActivity(), CoroutineScope {
    var checkId = false
    var uniqId = false
    var checkPw = false
    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        //아이디 중복 확인
        bt_check.setOnClickListener(View.OnClickListener {
            var id = et_id.text.toString()
            iDCheckRequest(id)
        })

        //비밀번호 확인
        et_pw_check.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                var pw = et_pw.text.toString()
                var check_pw = et_pw_check.text.toString()
                checkPw(pw,check_pw)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        btn_finish.setOnClickListener(View.OnClickListener{
            var id = et_id.text.toString()
            var pw = et_pw.text.toString()
            val scope = CoroutineScope(Dispatchers.Main)

            scope.launch(Dispatchers.Main) {
                withContext(coroutineContext){
                    signupRequest(id, pw)
                }
                if(checkId){
                    if(uniqId){
                        if(checkPw){
                            finish()
                        }else{
                            Toast.makeText(applicationContext, "비밀번호를 확인해주세요.", Toast.LENGTH_LONG).show()
                        }
                    }else{
                        Toast.makeText(applicationContext, "아이디를 변경해주세요.", Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(applicationContext, "아이디 중복확인 버튼을 눌러주세요.", Toast.LENGTH_LONG).show()
                }
            }
        })

        //val tb = findViewById<View>(R.id.board_tb) as Toolbar
        //setSupportActionBar(tb)

    }


    fun iDCheckRequest(id:String) {
        val url = "http://172.30.1.34:3000/users/signup/checkId"

        val testjson = JSONObject()
        try {
            testjson.put("id", id)
            val jsonString = testjson.toString()

            val requestQueue = Volley.newRequestQueue(this)
            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, testjson,
                Response.Listener { response ->
                    try {
                        Log.d("---------------------","데이터전송 성공")

                        val jsonObject = JSONObject(response.toString())

                        val resultId = jsonObject.getString("approve_id")

                        if(resultId == "OK"){
                            checkId = true
                            uniqId = true
                            tv_idcheck.setText("사용 가능한 아이디입니다.")
                            tv_idcheck.setTextColor(Color.GREEN)
                        }else{
                            et_id.setText(null)
                            tv_idcheck.setText("이미 존재하는 아이디입니다.")
                            tv_idcheck.setTextColor(Color.RED)
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
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun signupRequest(id:String, pw:String) {
        val url = "http://172.30.1.1:3000/users/signup"

        val testjson = JSONObject()
        try {
            testjson.put("id", id)
            testjson.put("password", pw)
            val jsonString = testjson.toString()

            val requestQueue = Volley.newRequestQueue(this)
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, url, testjson,
                Response.Listener { response ->
                    try {
                        Log.d("---------------------","데이터전송 성공")

                        val jsonObject = JSONObject(response.toString())

                        val resultId = jsonObject.getString("approve_id")
                        val resultPassword = "OK"//jsonObject.getString("approve_pw")

                        if((resultId == "OK") and (resultPassword == "OK")){
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

    fun checkPw(pw1:String, pw2:String){
        if(pw1!=null){
            if(pw1==pw2){
                tv_pwcheck.setText("비밀번호가 일치합니다.")
                tv_pwcheck.setTextColor(Color.GREEN)
                checkPw = true
            }else{
                tv_pwcheck.setText("비밀번호가 일치하지 않습니다")
                tv_pwcheck.setTextColor(Color.RED)
                checkPw = false
            }
        }

    }
}