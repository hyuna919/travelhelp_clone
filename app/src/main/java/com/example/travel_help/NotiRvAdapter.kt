package com.example.travel_help

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotiRvAdapter(val context:Context, val list: ArrayList<DataClassNotification>, val itemClick: (DataClassNotification) -> Unit):
    RecyclerView.Adapter<NotiRvAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.notification_rv, parent, false)
        return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(list[position], context)
    }

    inner class Holder(itemView: View?, itemClick:(DataClassNotification)->Unit) : RecyclerView.ViewHolder(itemView!!) {
        val msgSender = itemView?.findViewById<TextView>(R.id.noti_rv_item_name)
        val msgContent = itemView?.findViewById<TextView>(R.id.noti_rv_item_content)
        fun bind(msg: DataClassNotification, context: Context) {
            msgSender?.text = msg.post
            msgContent?.text = msg.comment

            itemView.setOnClickListener { itemClick(msg) }
        }
    }

}