package com.example.travel_help.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.travel_help.DataClass.DataClassAccount
import com.example.travel_help.R
import kotlinx.android.synthetic.main.login.*

class LoginActivity :AppCompatActivity(){
    private val SIGNUP_CODE = 100

    var accounts=arrayListOf<DataClassAccount>(
        DataClassAccount("root","1234"),
        DataClassAccount("guest","5678")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)


        //로그인 버튼
        bt_login.setOnClickListener(View.OnClickListener {
            var id = put_id.text.toString()
            var pw = put_pw.text.toString()

            for((i,account) in accounts.withIndex()){
                if(true){

                }
            }

        })

        //회원가입 버튼
        bt_signup.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, SignupActivity::class.java)
            //intent.putExtra("id",id)
            //intent.putExtra("pw",pw)
            startActivityForResult(intent,SIGNUP_CODE)
        })
    }
}