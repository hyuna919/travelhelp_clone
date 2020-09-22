package com.example.travel_help.RecyclerViewAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travel_help.DataClass.DataClassCountry
import com.example.travel_help.DataClass.DataClassSchedule
import com.example.travel_help.R

class ScheduleRvAdapter(val context:Context, val schedule: ArrayList<DataClassSchedule>, val itemClick: (DataClassSchedule) -> Unit):
    RecyclerView.Adapter<ScheduleRvAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.schedule_rv, parent, false)
        return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return schedule.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(schedule[position], context)
    }

    inner class Holder(itemView: View?, itemClick:(DataClassSchedule)->Unit) : RecyclerView.ViewHolder(itemView!!) {
        val city = itemView?.findViewById<TextView>(R.id.tv_city)
        val date_start = itemView?.findViewById<TextView>(R.id.tv_start)
        val date_end = itemView?.findViewById<TextView>(R.id.tv_end)
        fun bind(item: DataClassSchedule, context: Context) {
            city?.text = item.city
            date_start?.text = item.date_start
            date_end?.text = item.date_end

            itemView.setOnClickListener{itemClick(item)}
        }
    }

}