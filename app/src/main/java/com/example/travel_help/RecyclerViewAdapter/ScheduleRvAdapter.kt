package com.example.travel_help.RecyclerViewAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travel_help.DataClass.DataClassCountry
import com.example.travel_help.DataClass.DataClassSchedule
import com.example.travel_help.R

class ScheduleRvAdapter(val context:Context, val schedule: ArrayList<DataClassSchedule>, val itemClick: (DataClassSchedule, position:Int) -> Unit):
    RecyclerView.Adapter<ScheduleRvAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.schedule_rv, parent, false)
        return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return schedule.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(schedule[position], position)
    }

    inner class Holder(itemView: View?, itemClick:(DataClassSchedule, position:Int)->Unit) : RecyclerView.ViewHolder(itemView!!) {
        val flag = itemView?.findViewById<ImageView>(R.id.iv_flag)
        val city = itemView?.findViewById<TextView>(R.id.tv_city)
        val date = itemView?.findViewById<TextView>(R.id.tv_date)
        //val date_end = itemView?.findViewById<TextView>(R.id.tv_end)
        fun bind(item: DataClassSchedule, position: Int) {
            city?.text = item.city
            if(item.date != null){
                date?.text = item.date
            }else{
                date?.visibility=View.GONE
            }
            //국기 붙이기
            when(item.city){
                "이탈리아" -> flag?.setImageResource(R.drawable.flag_italy)
                "독일" -> flag?.setImageResource(R.drawable.flag_germany)
                "프랑스" -> flag?.setImageResource(R.drawable.flag_france)
                "영국" -> flag?.setImageResource(R.drawable.flag_uk)
                "벨기에" -> flag?.setImageResource(R.drawable.flag_belgium)
                else -> flag?.setImageResource(R.drawable.ic_add_rounded)

            }

            itemView.setOnClickListener{itemClick(item, position)}
        }
    }

}