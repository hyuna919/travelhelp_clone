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

class CountryRvAdapter(val itemClick: (country:String) -> Unit):
    RecyclerView.Adapter<CountryRvAdapter.Holder>() {

    private val list = arrayListOf(
        "이탈리아",
        "독일",
        "프랑스",
        "벨기에",
        "영국",
        "그리스"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_rv, parent, false)
        return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(list[position])
    }

    inner class Holder(itemView: View?, itemClick:(item:String)->Unit) : RecyclerView.ViewHolder(itemView!!) {
        val flag = itemView?.findViewById<ImageView>(R.id.iv_flag)
        val city = itemView?.findViewById<TextView>(R.id.tv_city)
        //val date_end = itemView?.findViewById<TextView>(R.id.tv_end)
        fun bind(item:String) {
            city?.text = item
            //국기 붙이기
            when(item){
                "이탈리아" -> flag?.setImageResource(R.drawable.flag_italy)
                "독일" -> flag?.setImageResource(R.drawable.flag_germany)
                "프랑스" -> flag?.setImageResource(R.drawable.flag_france)
                "영국" -> flag?.setImageResource(R.drawable.flag_uk)
                "벨기에" -> flag?.setImageResource(R.drawable.flag_belgium)
                "그리스" -> flag?.setImageResource(R.drawable.flag_greece)
                else -> flag?.setImageResource(R.drawable.ic_add_rounded)

            }

            itemView.setOnClickListener{itemClick(item)}
        }
    }

}