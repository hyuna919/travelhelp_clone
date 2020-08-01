package com.example.travel_help.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.travel_help.R
import kotlinx.android.synthetic.main.login.*

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
                    startActivity(intent)
                    finish()
                }
            }

        })

        //회원가입 버튼
        bt_signup.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, SignupActivity::class.java)
            startActivityForResult(intent,SIGNUP_CODE)
        })
    }
}