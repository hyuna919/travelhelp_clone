package com.example.travel_help.Activity

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
            checkId(id)
        })

        put_pw_check.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                var pw = put_pw.text.toString()
                var check_pw = put_pw_check.text.toString()
                checkPw(pw,check_pw)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }

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
            checkId = true
            notUniq = false
            tv_idcheck.setText("사용 가능한 아이디입니다.")
            tv_idcheck.setTextColor(Color.GREEN)
        }
    }


    fun checkPw(pw1:String, pw2:String){
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