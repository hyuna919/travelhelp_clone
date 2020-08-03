package com.example.travel_help.Activity

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import com.example.travel_help.R
import kotlinx.android.synthetic.main.signup.*


class SignupActivity : AppCompatActivity() {
    var checkId = false
    var uniqId = false
    var checkPw = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        //아이디 중복 확인
        bt_check.setOnClickListener(View.OnClickListener {
            var id = put_id.text.toString()
            checkId(id)
        })

        //비밀번호 확인
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

        btn_finish.setOnClickListener(View.OnClickListener{
            if(checkId){
                if(uniqId){
                    if(checkPw){
                        //완료 인텐트
                    }else{
                        Toast.makeText(applicationContext, "비밀번호를 확인해주세요.", Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(applicationContext, "아이디를 변경해주세요.", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(applicationContext, "아이디 중복확인 버튼을 눌러주세요.", Toast.LENGTH_LONG).show()
            }
        })

        //val tb = findViewById<View>(R.id.board_tb) as Toolbar
        //setSupportActionBar(tb)

    }

    fun checkId(id:String){
        var uniq = true
        var idList = intent.getStringArrayExtra("id")

        for(list in idList){
            if(list==id){
                Log.d("-----------------------","ddddddddddddd")
                put_id.setText(null)
                tv_idcheck.setText("이미 존재하는 아이디입니다.")
                tv_idcheck.setTextColor(Color.RED)
                uniq = false
                break
            }
        }

        if(uniq){
            checkId = true
            uniqId = true
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