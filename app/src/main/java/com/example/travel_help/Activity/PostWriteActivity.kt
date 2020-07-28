package com.example.travel_help.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.travel_help.DataClass.DataClassPost
import com.example.travel_help.R
import kotlinx.android.synthetic.main.post_write.*

class PostWriteActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_write)

        //저장 버튼(pwr_btn)
        pwr_btn.setOnClickListener(View.OnClickListener {
            //setPost()
            //val post = DataClassPost(pwr_title.text.toString(), pwr_date.text.toString().toInt(), pwr_airport.text.toString(), pwr_content.text.toString() )
            val intent = Intent(this,BoardActivity::class.java)
            intent.putExtra("title",pwr_title.text.toString())
            intent.putExtra("date",pwr_date.text.toString())
            intent.putExtra("airport",pwr_airport.text.toString())
            intent.putExtra("content",pwr_content.text.toString())
            setResult(RESULT_OK,intent)

            finish()
        })

    }
}