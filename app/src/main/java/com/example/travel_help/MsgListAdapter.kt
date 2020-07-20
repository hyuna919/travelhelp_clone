package com.example.travel_help

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MsgListAdapter(val context:Context, val list: ArrayList<DataClassMsg>, val itemClick: (DataClassMsg) -> Unit):
    RecyclerView.Adapter<MsgListAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.msg_list_rv, parent, false)
        return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(list[position], context)
    }

    inner class Holder(itemView: View?, itemClick:(DataClassMsg)->Unit) : RecyclerView.ViewHolder(itemView!!) {
        val msgSender = itemView?.findViewById<TextView>(R.id.msg_rv_item_name)
        val msgContent = itemView?.findViewById<TextView>(R.id.msg_rv_item_content)
        fun bind(msg: DataClassMsg, context: Context) {
            msgSender?.text = msg.name
            msgContent?.text = msg.contents

            itemView.setOnClickListener{itemClick(msg)}
        }
    }

}