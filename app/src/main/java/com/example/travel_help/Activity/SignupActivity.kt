package com.example.travel_help.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toolbar
import com.example.travel_help.R
import kotlinx.android.synthetic.main.signup.*


class SignupActivity : AppCompatActivity() {
    var checkId = false
    var checkPw = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        bt_check.setOnClickListener(View.OnClickListener {
            var id = bt_check.text.toString()

            checkId(id)
        })

        //val tb = findViewById<View>(R.id.board_tb) as Toolbar
        //setSupportActionBar(tb)

    }

    fun checkId(id:String){
        var idList = intent.getStringArrayListExtra("id")
        for(list in idList){
            if(list==id){

            }
        }

    }
}