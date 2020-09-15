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
import com.example.travel_help.R

class ChatRvAdapter(val context: Context, val chatList: ArrayList<DataClassChatting>, val itemClick: (DataClassChatting) -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun addItem(item: DataClassChatting){
        if(chatList != null){
            chatList.add(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val id = App.prefs.getString("id","fault")
        //송신자와 유저 id가 일치하면
        if(chatList.get(position).sender==id){
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
            return Holder_User(view, itemClick)
        }else{
            view = LayoutInflater.from(context).inflate((R.layout.chat_rv_other), parent, false)
            return Holder_Other(view, itemClick)
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is Holder_User){
            (holder as Holder_User).date?.setText(chatList.get(position).time)
            (holder as Holder_User).msg?.setText(chatList.get(position).message)
        }else if(holder is Holder_Other){
            (holder as Holder_Other).date?.setText(chatList.get(position).time)
            (holder as Holder_Other).msg?.setText(chatList.get(position).message)
        }
    }

    inner class Holder_User(itemView: View?, itemClick:(DataClassChatting)->Unit) : RecyclerView.ViewHolder(itemView!!) {
        val date = itemView?.findViewById<TextView>(R.id.date)
        val msg = itemView?.findViewById<TextView>(R.id.msg)

    }

    inner class Holder_Other(itemView: View?, itemClick:(DataClassChatting)->Unit) : RecyclerView.ViewHolder(itemView!!) {
        val date = itemView?.findViewById<TextView>(R.id.date)
        val msg = itemView?.findViewById<TextView>(R.id.msg)

    }
}