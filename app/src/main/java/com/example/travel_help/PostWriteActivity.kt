package com.example.travel_help

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.post_write.*

class PostWriteActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_write)

        //저장 버튼(pwr_btn)
        pwr_btn.setOnClickListener(View.OnClickListener {
            //setPost()
            finish()
        })


    }
}