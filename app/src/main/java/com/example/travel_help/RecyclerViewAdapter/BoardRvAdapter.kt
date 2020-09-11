package com.example.travel_help.RecyclerViewAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travel_help.DataClass.DataClassPost
import com.example.travel_help.R

class BoardRvAdapter(val itemClick: (DataClassPost, position: Int) -> Unit):
    RecyclerView.Adapter<BoardRvAdapter.Holder>() {
    private val list = arrayListOf<DataClassPost>()

    fun setPosts(list: ArrayList<DataClassPost>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun addPosts(list: ArrayList<DataClassPost>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun setLoadingView(b: Boolean) {
        if (b) {
            android.os.Handler().post {
                //this.list.add(null)
                notifyItemInserted(list.size - 1)
            }
        } else {
            if (this.list[list.size - 1] == null) {
                this.list.removeAt(list.size - 1)
                notifyItemRemoved(list.size)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.board_rv, parent, false)
        val holder = Holder(view, itemClick)

        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(list[position], position)
    }

    inner class Holder(itemView: View?, itemClick:(DataClassPost, position: Int)->Unit) : RecyclerView.ViewHolder(itemView!!) {
        val title = itemView?.findViewById<TextView>(R.id.board_rv_title)
        val content = itemView?.findViewById<TextView>(R.id.board_rv_content)
        val airport = itemView?.findViewById<TextView>(R.id.board_rv_airport)

        fun bind(post: DataClassPost, position: Int) {
            title?.text = post.title
            content?.text = post.content
            airport?.text = "["+post.airport+"]"
            itemView.setOnClickListener{
                itemClick(post, position)
            }
        }
    }
}