package com.example.travel_help.Activity

import android.app.Activity
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
import kotlinx.android.synthetic.main.login.bt_login
import kotlinx.android.synthetic.main.welcome.*
import org.json.JSONException
import org.json.JSONObject

class WelcomeActivity :AppCompatActivity() {
    private val REQUEST = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome)


        //로그인 버튼
        bt_login.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent,REQUEST)
        })

        //회원가입 버튼
        bt_signup.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RESULT_OK){
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}