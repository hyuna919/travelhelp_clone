package com.example.travel_help.Activity

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.travel_help.App
import com.example.travel_help.DataClass.DataClassPost
import com.example.travel_help.R
import kotlinx.android.synthetic.main.board.*
import kotlinx.android.synthetic.main.post_read.*
import org.json.JSONException
import org.json.JSONObject


class PostReadActivity : AppCompatActivity() {
    private val POST_CHANGE=2000
    private var isChanged ="no"     //바뀌지않음:no, 수정됨:changed, 삭제됨:deleted
    private var post_id:String?=""
    private var title:String?=""
    private var date:String?=""
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
            intent.putExtra("post_id",post_id)
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
                val post_id = intent?.getStringExtra("post_id")
                deletePost(post_id)
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

    //게시글 삭제 다이얼로그에서 사용자가 삭제 동의시
    fun deletePost(post_id:String?){
        del_request(post_id)
        isChanged="deleted"
        val intent = Intent(this,BoardActivity::class.java)
        intent.putExtra("isChanged",isChanged)
        setResult(RESULT_OK,intent)
        finish()
    }



    fun bind(intent:Intent?){
        post_id = intent?.getStringExtra("post_id")
        title = intent?.getStringExtra("title")
        date = intent?.getStringExtra("date")
        airport = intent?.getStringExtra("airport")
        content = intent?.getStringExtra("content")

        pr_title?.text = title
        pr_date?.text = date
        pr_airport?.text = airport
        pr_contents?.text = content
    }

    override fun onBackPressed() {
        //게시글이 수정됐으면
        if(isChanged=="changed"){
            intent = Intent(this,BoardActivity::class.java)
            intent.putExtra("title",pr_title.text.toString())
            intent.putExtra("date", pr_date.text.toString())
            intent.putExtra("airport",pr_airport.text.toString())
            intent.putExtra("content",pr_contents.text.toString())
            intent.putExtra("isChanged",isChanged)
            setResult(RESULT_OK,intent)
        }else{
            setResult(RESULT_CANCELED,intent)
        }
        super.onBackPressed()
    }

    fun del_request(post_id:String?) {
        val url = "http://172.30.1.34:3000/posts/deletePost"
        Log.d("---------------------","삭제 시도")

        val testjson = JSONObject()
        try {
            val tmp =App.prefs.getString("token","aa")
            testjson.put("accessToken", tmp)//세션 만들기 전이라 임시로
            testjson.put("id", post_id)
            Log.d("---------------------",tmp)

            val requestQueue = Volley.newRequestQueue(this)
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, url, testjson,
                Response.Listener { response ->
                    try {
                        Log.d("00000000000000","삭제 성공")

                        val jsonObject = JSONObject(response.toString())

                        val result = jsonObject.getString("approve")


                        if(result == "OK"){
                            finish()
                            Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },

                Response.ErrorListener { error ->
                    error.printStackTrace()
                })

            jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            requestQueue.add(jsonObjectRequest)
            //
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}