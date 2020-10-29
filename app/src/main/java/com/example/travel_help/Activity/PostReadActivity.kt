package com.example.travel_help.Activity

import android.Manifest
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
import com.android.volley.toolbox.Volley
import com.example.travel_help.App
import com.example.travel_help.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.post_read.*
import org.json.JSONException
import org.json.JSONObject


class PostReadActivity : AppCompatActivity(), OnMapReadyCallback {
    private val POST_CHANGE=2000
    private var isChanged ="no"     //바뀌지않음:no, 수정됨:changed, 삭제됨:deleted
    private var post_id:String?=""
    private var type:String?=""
    private var title:String?=""
    private var contents:String?=""
    private var writer_id:String?=""
    private var createdAt:String?=""
    private var recommended :String?=""

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: MyLocationCallBack


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_read)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initLocation()

        bind(intent)

        //글 수정 버튼
        btn_update.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, PostWriteActivity::class.java)
            intent.putExtra("post_id",post_id)
            intent.putExtra("title",title)
            intent.putExtra("type",type)
            intent.putExtra("contents",contents)
            intent.putExtra("writer_id",writer_id)
            intent.putExtra("createdAt",createdAt)
            intent.putExtra("recommended",recommended)
            //PostWriteActivity에 수정용이라는 걸 알리기 위한 정보
            intent.putExtra("chancreatedAtge",true)
            startActivityForResult(intent,POST_CHANGE)
        })

        //삭제버튼 클릭
        btn_delete.setOnClickListener(View.OnClickListener {
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

        //쪽지 버튼
        iv_send.setOnClickListener(View.OnClickListener {
            //유저 아이디
            val user_id = App.prefs.getString("id","fail")
            val other_id = intent?.getStringExtra("writer_id")
            //채팅방이름="처음보낸사람_받는사람"
            var roomNumber = user_id + "_" + other_id

            room_request(user_id,other_id,roomNumber)


            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("roomNumber",roomNumber)
            intent.putExtra("other_id",other_id)
            startActivity(intent)
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
        type = intent?.getStringExtra("type")
        title = intent?.getStringExtra("title")
        recommended = "0"
        contents = intent?.getStringExtra("content")
        title = intent?.getStringExtra("title")

        tv_type?.text = type
        tv_title?.text = title
        tv_recommended?.text = recommended
        tv_contents?.text = contents
        Log.d("---------------------------------",type)
    }

    override fun onBackPressed() {
        //게시글이 수정됐으면
        if(isChanged=="changed"){
            intent = Intent(this,BoardActivity::class.java)
            intent.putExtra("title",tv_title.text.toString())
            intent.putExtra("date", tv_createdAt.text.toString())
            intent.putExtra("airport",tv_recommended.text.toString())
            intent.putExtra("content",tv_contents.text.toString())
            intent.putExtra("isChanged",isChanged)
            setResult(RESULT_OK,intent)
        }else{
            setResult(RESULT_CANCELED,intent)
        }
        super.onBackPressed()
    }

    private fun room_request(user_id:String,other_id:String?,roomNumber:String){
        //private fun request():ArrayList<DataClassMsg>{
        val url = "http://172.30.1.34:3000/messages/newRoom"
        val testjson = JSONObject()
        try {
            val tmp = App.prefs.getString("token","fail")
            testjson.put("accessToken", tmp)
            testjson.put("roomNumber", roomNumber)
            testjson.put("user_id", user_id)
            testjson.put("other_id", other_id)


            val requestQueue = Volley.newRequestQueue(this)
            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, testjson,
                Response.Listener { response ->
                    try {
                        Log.d("---------------------","룸넘버 성공")

                        val jsonObject = JSONObject(response.toString())
                        val result = jsonObject.getString("response")

                        if(result == "OK"){
                            finish()
                            Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show()
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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val seoul = LatLng(37.715133, 126.734086)
        mMap.addMarker(MarkerOptions().position(seoul).title("Marker in Seoul"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(seoul))
    }

    private fun initLocation(){
        fusedLocationProviderClient = FusedLocationProviderClient(this)

        locationCallback = MyLocationCallBack()

        locationRequest = LocationRequest()

        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
    }

    override fun onResume() {
        super.onResume()
    }

    inner class MyLocationCallBack : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val location = locationResult?.lastLocation

            location?.run {
                val latLng = LatLng(latitude,longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17f))
            }
        }
    }
}