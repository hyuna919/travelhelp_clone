package com.example.travel_help.RecyclerViewAdapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.travel_help.Activity.PostReadActivity
import com.example.travel_help.DataClass.DataClassPost
import com.example.travel_help.R

class BoardRvAdapter(val context:Context, val list: ArrayList<DataClassPost>):
    RecyclerView.Adapter<BoardRvAdapter.Holder>() {
    lateinit var clickeditem: (DataClassPost)->Unit
    lateinit var bundle:Bundle
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.board_rv, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(list[position], context)

    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val title = itemView?.findViewById<TextView>(R.id.board_rv_title)
        val content = itemView?.findViewById<TextView>(R.id.board_rv_content)
        fun bind(post: DataClassPost, context: Context) {
            title?.text = post.title
            content?.text = post.content
            itemView.setOnClickListener(View.OnClickListener {
                val intent = Intent(context, PostReadActivity::class.java)
                //intent.putExtra("title",post.title)
                intent.putExtra("title",list[adapterPosition].title)
                context.startActivity(intent)
            })
        }
    }


}