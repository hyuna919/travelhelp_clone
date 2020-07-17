package com.example.travel_help

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MypageRvAdapter(val context:Context, val list: ArrayList<DataClassMypage>, val itemClick: (DataClassMypage) -> Unit):
    RecyclerView.Adapter<MypageRvAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.mypage_rv, parent, false)
        return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(list[position], context)
    }

    inner class Holder(itemView: View?, itemClick:(DataClassMypage)->Unit) : RecyclerView.ViewHolder(itemView!!) {
        val mypageSender = itemView?.findViewById<TextView>(R.id.mypage_rv_item_name)
        fun bind(msg: DataClassMypage, context: Context) {
            mypageSender?.text = msg.service

            itemView.setOnClickListener{itemClick(msg)}
        }
    }

}