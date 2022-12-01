package com.example.nightlifeapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmergencyContactAdapter(private val context: Context, private val contacts: ArrayList<EmergencyContact>):
    RecyclerView.Adapter<EmergencyContactAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val tvContactName = itemView.findViewById<TextView>(R.id.tvContactName)
        val tvRP = itemView.findViewById<TextView>(R.id.tvRelationship)
        val tvPhoneNumber = itemView.findViewById<TextView>(R.id.tvPhoneNumber)

        fun bind(contact: EmergencyContact){
            tvContactName.text = contact.getName()
            tvRP.text = contact.getRelationship()
            tvPhoneNumber.text = contact.getPhoneNumber()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val contactView = LayoutInflater.from(context).inflate(R.layout.item_contact,parent,false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contacts[position]
        return holder.bind(contact)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }
}