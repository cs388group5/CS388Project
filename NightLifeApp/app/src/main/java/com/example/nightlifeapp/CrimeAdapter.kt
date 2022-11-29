package com.example.nightlifeapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import java.util.*

class CrimeAdapter(private val context: Context,private val crimes:List<Crime>): RecyclerView.Adapter<CrimeAdapter.ViewHolder>()
{
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val incident = itemView.findViewById<TextView>(R.id.tvIncident)
        private val description = itemView.findViewById<TextView>(R.id.tvDescription)
        private val date = itemView.findViewById<TextView>(R.id.tvDateTime)
        private val location = itemView.findViewById<TextView>(R.id.tvLocation)
        init{
            itemView.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            val crime = crimes[adapterPosition]

        }

        fun bind(crime: Crime) {
            incident.text = crime.getCrimeType()
            description.text = crime.getDescription()
            date.text = crime.getDate().toString()
            location.text = crime.getLocation()


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_crime,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val crime = crimes[position]
         holder.bind(crime)
    }

    override fun getItemCount(): Int {
        return crimes.size
    }

}