package com.example.icare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter (private val AppointList: ArrayList<Appointment>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val tvDate : TextView= itemView.findViewById(R.id.erDate)
        val tvTime : TextView= itemView.findViewById(R.id.erTime)
        val tvDoctor : TextView= itemView.findViewById(R.id.erDoctor)
        val tvPoli : TextView= itemView.findViewById(R.id.erPoliklinik)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvDate.text = AppointList[position].date
        holder.tvPoli.text = AppointList[position].poli
        holder.tvDoctor.text = AppointList[position].doctor
        holder.tvTime.text = AppointList[position].time
    }

    override fun getItemCount(): Int {
        return AppointList.size
    }
}