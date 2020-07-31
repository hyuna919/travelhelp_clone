package com.example.travel_help.Activity

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
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
    private var isChanged ="no"     //바뀌지않음:no, 수정됨:changed, 삭제됨:deleted
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

        //삭제버튼 클릭
        pr_btn_delete.setOnClickListener(View.OnClickListener {
            var dialog = AlertDialog.Builder(this)
            dialog.setTitle("게시글 삭제")
            dialog.setMessage("게시글을 삭제하시겠습니까?")

            fun toast_p() {
                deletePost()
            }
            fun toast_n(){
            }

            var dialog_listener = object: DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    when(which){
                        DialogInterface.BUTTON_POSITIVE ->
                            toast_p()
                        DialogInterface.BUTTON_NEGATIVE ->
                            toast_n()
                    }
                }
            }

            dialog.setPositiveButton("YES",dialog_listener)
            dialog.setNegativeButton("NO",dialog_listener)
            dialog.show()
        })
    }

    //수정후 응답받으면
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==RESULT_OK){
            when (requestCode) {
                POST_CHANGE -> {
                    bind(data)
                    isChanged="changed"
                }
            }
        }else{
            return
        }
    }

    //게시글 삭제 다이얼로그에서 삭제 확인시
    fun deletePost(){
        isChanged="deleted"
        val intent = Intent(this,BoardActivity::class.java)
        intent.putExtra("isChanged",isChanged)
        setResult(RESULT_OK,intent)
        finish()
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

    override fun onBackPressed() {
        //게시글이 수정됐으면
        if(isChanged=="changed"){
            intent = Intent(this,BoardActivity::class.java)
            intent.putExtra("title",pr_title.text.toString())
            intent.putExtra("date", pr_date.text.toString().toInt())
            intent.putExtra("airport",pr_airport.text.toString())
            intent.putExtra("content",pr_contents.text.toString())
            intent.putExtra("isChanged",isChanged)
            setResult(RESULT_OK,intent)
        }else{
            setResult(RESULT_CANCELED,intent)
        }
        super.onBackPressed()
    }

}