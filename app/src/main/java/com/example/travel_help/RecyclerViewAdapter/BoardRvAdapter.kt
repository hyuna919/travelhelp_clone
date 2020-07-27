package com.example.travel_help.RecyclerViewAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travel_help.DataClass.DataClassBoard
import com.example.travel_help.R

class BoardRvAdapter(val context:Context, val list: ArrayList<DataClassBoard>, val itemClick: (DataClassBoard) -> Unit):
    RecyclerView.Adapter<BoardRvAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.board_rv, parent, false)
        return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(list[position], context)
    }

    inner class Holder(itemView: View?, itemClick:(DataClassBoard)->Unit) : RecyclerView.ViewHolder(itemView!!) {
        val title = itemView?.findViewById<TextView>(R.id.board_rv_title)
        val content = itemView?.findViewById<TextView>(R.id.board_rv_content)
        fun bind(post: DataClassBoard, context: Context) {
            title?.text = post.title
            content?.text = post.summary

            itemView.setOnClickListener{itemClick(post)}
        }
    }

}