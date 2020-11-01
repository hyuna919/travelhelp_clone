package com.example.travel_help.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.travel_help.DataClass.DataClassMypage
import com.example.travel_help.Fragment.MypageFragment
import com.example.travel_help.RecyclerViewAdapter.MypageRvAdapter
import com.example.travel_help.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.login.*


class MypageActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.navigation_chatting -> {
                val intent = Intent(this, MsgListActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.navigation_mypage -> {
                val intent = Intent(this, NotiActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        false
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mypage)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.constraint , MypageFragment())
            .commit()
        val navView: BottomNavigationView = findViewById(R.id.navigation)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)


    }
}