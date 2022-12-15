package com.example.nightlifeapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class EmergencyContactAdapter(private val context: Context, private val contacts: ArrayList<EmergencyContact>):
    RecyclerView.Adapter<EmergencyContactAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val tvContactName = itemView.findViewById<TextView>(R.id.tvContactName)
        val tvRP = itemView.findViewById<TextView>(R.id.tvRelationship)
        val tvPhoneNumber = itemView.findViewById<TextView>(R.id.tvPhoneNumber)
        val btDelete = itemView.findViewById<Button>(R.id.deleteContactBtn)
        val btCall = itemView.findViewById<ImageButton>(R.id.btCall)

        init {
            itemView.setOnClickListener(this)
            btDelete.setOnClickListener(this)
            btCall.setOnClickListener(this)
        }
        fun bind(contact: EmergencyContact){
            tvContactName.text = contact.getName()
            tvRP.text = "(${contact.getRelationship()})"
            tvPhoneNumber.text = contact.getPhoneNumber()
        }

        override fun onClick(p0: View?) {
            val contact = contacts[adapterPosition]
            Log.i("Emergency Adapter", p0.toString())
            if (p0 != null) {
                if (p0.equals(btDelete)){
                    removeAt(adapterPosition)
                }
                if (p0.equals(btCall)){
                    callPhone(contact.getPhoneNumber().toString())
                }
            }
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
    // Function to remove contact from the recycler view
    fun removeAt(position: Int){
        // Do routines to remove the contact on the database
        deleteContact(contacts[position])
        contacts.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,itemCount)
    }
    // Function to remove contact from the server
    fun deleteContact (contact: EmergencyContact){
        contact.deleteInBackground{
            e ->
            if (e != null){
                e.printStackTrace()
                Toast.makeText(context,"Couldn't delete contact", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context,"Contact successfully deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun callPhone(number: String){
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$number")
        Toast.makeText(context, "Calling $number", Toast.LENGTH_SHORT).show()
        startActivity(context,callIntent, Bundle())
    }
}