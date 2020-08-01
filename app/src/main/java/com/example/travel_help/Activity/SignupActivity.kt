package com.example.travel_help.Activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toolbar
import com.example.travel_help.R
import kotlinx.android.synthetic.main.signup.*


class SignupActivity : AppCompatActivity() {
    var checkId = false
    var notUniq = false
    var checkPw = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        bt_check.setOnClickListener(View.OnClickListener {
            var id = put_id.text.toString()
            Log.d("------------","1111111111")
            checkId(id)
        })

        //val tb = findViewById<View>(R.id.board_tb) as Toolbar
        //setSupportActionBar(tb)

    }

    fun checkId(id:String){
        notUniq = false
        var idList = intent.getStringArrayExtra("id")

        for(list in idList){
            if(list==id){
                put_id.setText(null)
                tv_idcheck.setText("이미 존재하는 아이디입니다.")
                tv_idcheck.setTextColor(Color.RED)
                notUniq = true
                break
            }
        }

        if(!notUniq){

            Log.d("------------","0000000000")
            checkId = true
            notUniq = false
            tv_idcheck.setText("사용 가능한 아이디입니다.")
            tv_idcheck.setTextColor(Color.GREEN)
        }



    }
}