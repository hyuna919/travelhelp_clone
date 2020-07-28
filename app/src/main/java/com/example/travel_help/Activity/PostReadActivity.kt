package com.example.travel_help.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.travel_help.DataClass.DataClassPost
import com.example.travel_help.R
import kotlinx.android.synthetic.main.board.*
import kotlinx.android.synthetic.main.post_read.*


class PostReadActivity : AppCompatActivity() {
    private val POST_CHANGE=2000
    private var title:String?=""
    private var date:Int?=-1
    private var airport:String?=""
    private var content :String?=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_read)
        //val title = intent.getParcelableExtra<DataClassPost>("title")

        bind(intent)

        //글 수정 버튼
        pr_btn_change.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, PostWriteActivity::class.java)
            intent.putExtra("title",title)
            intent.putExtra("date",date)
            intent.putExtra("airport",airport)
            intent.putExtra("content",content)
            //PostWriteActivity에 수정용이라는 걸 알리기 위한 정보
            intent.putExtra("change",true)
            startActivityForResult(intent,POST_CHANGE)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==RESULT_OK){
            when (requestCode) {
                POST_CHANGE -> {
                    bind(data)
                }
            }
        }else{
            return
        }
    }

    fun bind(intent:Intent?){
        title = intent?.getStringExtra("title")
        date = intent?.getIntExtra("date",-3)
        airport = intent?.getStringExtra("airport")
        content = intent?.getStringExtra("content")

        pr_title?.text = title
        pr_date?.text = date.toString()
        pr_airport?.text = airport
        pr_contents?.text = content
    }

}