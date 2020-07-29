package com.example.travel_help.Activity

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.travel_help.DataClass.DataClassPost
import com.example.travel_help.R
import kotlinx.android.synthetic.main.post_read.*
import kotlinx.android.synthetic.main.post_write.*

class PostWriteActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_write)

        val change = intent.getBooleanExtra("change",false)
        if(change==true){
            val title = intent.getStringExtra("title")
            val date = intent.getIntExtra("date",-1)
            val airport = intent.getStringExtra("airport")
            val content = intent.getStringExtra("content")

            pwr_title.setText(title)
            //게시글 수정시 제목수정 불가
            pwr_title.isFocusable=false
            pwr_title.isFocusableInTouchMode=false
            pwr_date?.setText(date.toString())
            pwr_airport?.setText(airport)
            pwr_content?.setText(content)
        }

        //저장 버튼(pwr_btn)
        pwr_btn.setOnClickListener(View.OnClickListener {
            //setPost()
            lateinit var intent:Intent
            if(change==true){
                intent = Intent(this,PostReadActivity::class.java)
            }else{
                intent = Intent(this,BoardActivity::class.java)
            }

            intent.putExtra("title",pwr_title.text.toString())
            intent.putExtra("date", pwr_date.text.toString().toInt())
            intent.putExtra("airport",pwr_airport.text.toString())
            intent.putExtra("content",pwr_content.text.toString())

            setResult(RESULT_OK,intent)

            finish()
        })

    }
}