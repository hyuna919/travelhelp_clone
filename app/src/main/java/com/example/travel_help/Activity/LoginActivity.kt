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
            Log.d("*****************************",keys[0])
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
                    Log.d("------------------",accounts.toString())
                }
            }
        }else{
            return
        }
    }

    fun request() {
        //url 요청주소 넣는 editText를 받아 url만들기
        val url = "http://172.30.1.44:3000"

        //JSON형식으로 데이터 통신을 진행합니다!
        val testjson = JSONObject()
        try {
            //입력해둔 edittext의 id와 pw값을 받아와 put해줍니다 : 데이터를 json형식으로 바꿔 넣어주었습니다.
            testjson.put("id", put_id.text.toString())
            testjson.put("password", put_pw.text.toString())
            val jsonString = testjson.toString() //완성된 json 포맷

            //이제 전송해볼까요?
            val requestQueue = Volley.newRequestQueue(this)
            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, testjson,
                Response.Listener { response ->
                    //데이터 전달을 끝내고 이제 그 응답을 받을 차례입니다.
                    try {
                        Log.d("---------------------","데이터전송 성공")

                        //받은 json형식의 응답을 받아
                        val jsonObject = JSONObject(response.toString())

                        //key값에 따라 value값을 쪼개 받아옵니다.
                        val resultId = jsonObject.getString("approve_id")
                        val resultPassword = jsonObject.getString("approve_pw")

                        //만약 그 값이 같다면 로그인에 성공한 것입니다.
                        if ((resultId == "OK") and (resultPassword == "OK")) {

                            //이 곳에 성공 시 화면이동을 하는 등의 코드를 입력하시면 됩니다.
                        } else {
                            //로그인에 실패했을 경우 실행할 코드를 입력하시면 됩니다.
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                //서버로 데이터 전달 및 응답 받기에 실패한 경우 아래 코드가 실행됩니다.
                Response.ErrorListener { error ->
                    error.printStackTrace()
                    //Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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