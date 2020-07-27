package com.example.travel_help.Activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.travel_help.R
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