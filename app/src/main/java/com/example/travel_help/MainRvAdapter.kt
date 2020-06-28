package com.example.travel_help

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainRvAdapter(val context:Context, val countryList: ArrayList<DataClassCountry>, val itemClick: (DataClassCountry) -> Unit):
    RecyclerView.Adapter<MainRvAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.main_rv, parent, false)
        return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(countryList[position], context)
    }

    inner class Holder(itemView: View?, itemClick:(DataClassCountry)->Unit) : RecyclerView.ViewHolder(itemView!!) {
        val countryList = itemView?.findViewById<TextView>(R.id.board_rv_title)
        fun bind(country: DataClassCountry, context: Context) {
            countryList?.text = country.countryName

            itemView.setOnClickListener{itemClick(country)}
        }
    }

}