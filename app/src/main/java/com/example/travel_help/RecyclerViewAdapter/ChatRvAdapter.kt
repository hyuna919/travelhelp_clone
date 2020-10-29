package com.example.travel_help.RecyclerViewAdapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travel_help.App
import com.example.travel_help.DataClass.DataClassChatting
import com.example.travel_help.DataClass.DataClassPost
import com.example.travel_help.R

class ChatRvAdapter(val context: Context)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val list = arrayListOf<DataClassChatting>()

    fun setItem(list: ArrayList<DataClassChatting>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
    fun addItem(item: DataClassChatting){
        if(list != null){
            list.add(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val id = App.prefs.getString("id","fault")
        //송신자와 유저 id가 일치하면
        if(list.get(position).sender==id){
            return 1
        }else{
            return 2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        //유저의 메세지
        if(viewType==1){
            view = LayoutInflater.from(context).inflate((R.layout.chat_rv_user), parent, false)
            return Holder_User(view)
        }else{
            view = LayoutInflater.from(context).inflate((R.layout.chat_rv_other), parent, false)
            return Holder_Other(view)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is Holder_User){
            (holder as Holder_User).time?.setText(list.get(position).time)
            (holder as Holder_User).msg?.setText(list.get(position).message)
        }else if(holder is Holder_Other){
            (holder as Holder_Other).time?.setText(list.get(position).time)
            (holder as Holder_Other).msg?.setText(list.get(position).message)
        }
    }

    inner class Holder_User(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val time = itemView?.findViewById<TextView>(R.id.time)
        val msg = itemView?.findViewById<TextView>(R.id.msg)

    }

    inner class Holder_Other(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val time = itemView?.findViewById<TextView>(R.id.time)
        val msg = itemView?.findViewById<TextView>(R.id.msg)

    }
}