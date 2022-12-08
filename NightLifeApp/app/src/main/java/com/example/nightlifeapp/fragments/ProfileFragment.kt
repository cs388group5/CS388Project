package com.example.nightlifeapp.fragments

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nightlifeapp.*
import com.example.nightlifeapp.R
import com.parse.*


class ProfileFragment : Fragment(), FormDialogFragment.FormDialogListener{
    lateinit var contactsRv: RecyclerView
    lateinit var adapter: EmergencyContactAdapter
    lateinit var addButton: Button
    lateinit var btEdit: Button
    lateinit var tvName: TextView
    lateinit var tvEmail: TextView
    lateinit var ivProfile: ImageView
    lateinit var userInfo: MutableList<ParseUser>
    var contacts: MutableList<EmergencyContact> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        userInfo = queryUser(ParseUser.getCurrentUser())
        btEdit = view.findViewById(R.id.btEditProfile)
        tvName = view.findViewById(R.id.tvName)
        tvEmail = view.findViewById(R.id.tvEmail)
        ivProfile = view.findViewById(R.id.ivProfile)
        contactsRv = view.findViewById(R.id.rvEmergencyContact)
        adapter = EmergencyContactAdapter(requireContext(), contacts as ArrayList<EmergencyContact>)
        contactsRv.adapter = adapter
        contactsRv.layoutManager = LinearLayoutManager(requireContext())
        addButton = view.findViewById(R.id.btAdd)

        val userH : User = userInfo.get(0) as User

        tvName.text = String.format("%s %s",userH.getFirstName(), userH.getLastName())
        tvEmail.text = userH.email

        addButton.setOnClickListener {
            val dialog = FormDialogFragment()
            dialog.setTargetFragment(this, 1)
            dialog.show(parentFragmentManager,"MyCustomDialog")
        }

        // Inflating profile fragment layout on edit button click
        btEdit.setOnClickListener {
            goToEditProfile()
        }
        queryContact(ParseUser.getCurrentUser())
    }

    override fun onDialogPositiveClick(x: List<String>) {
        if (x.size != 4){
            Toast.makeText(requireContext(),"Missing entries", Toast.LENGTH_SHORT).show()
        } else {
            insertContact(x.get(0),x.get(1),x.get(2), x.get(3))
        }
    }

    override fun onDialogNegativeClick(x: List<String>) {
    }

    fun insertContact (firstName: String, lastName: String, phoneNumber: String, relationShip: String){
        val contact = EmergencyContact()
        val user: ParseUser = ParseUser.getCurrentUser()
        if (user == null){
            Toast.makeText(requireContext(),"You are not logged", Toast.LENGTH_SHORT).show()
            return
        }
        contact.put("user_id", user) //To be changed
        contact.setFirstName(firstName)
        contact.setLastName(lastName)
        contact.setRelationship(relationShip)
        contact.setPhoneNumber(phoneNumber) //To be changed
        contact.saveInBackground{
                exception ->
            if(exception != null){
                exception.printStackTrace()
                Log.e("Error","Error while saving Contact")
                Log.e("Error","$exception")
                Toast.makeText(requireContext(), "Error saving the post", Toast.LENGTH_SHORT).show()
            } else {
                Log.i("Success","Contact saved successfully")
                Toast.makeText(requireContext(), "Successfully saved post", Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
            }
        }

    }

    fun queryContact(user: ParseUser) {

        //specify which class to query
        val query: ParseQuery<EmergencyContact> = ParseQuery.getQuery(EmergencyContact::class.java)

        //find contacts
        query.whereEqualTo("user_id",user)
        query.addDescendingOrder("createdAt")
        query.setLimit(20)
        query.findInBackground(object : FindCallback<EmergencyContact> {
            override fun done(contactList: MutableList<EmergencyContact>?, e: ParseException?) {
                if(e!=null){
                    Log.e(HomeFragment.TAG,"Error fetching contacts")
                }else {
                    if(contactList!=null){
                        for(contact in contactList){
                            Log.i(HomeFragment.TAG,"Post: "+contact.getName())
                        }
                        contacts.addAll(contactList)
                        adapter.notifyDataSetChanged()

                    }
                }
            }

        })
    }

    fun queryUser(user: ParseUser): MutableList<ParseUser> {
        //specify which class to query
        val query= ParseUser.getQuery()

        //find User
        query.whereEqualTo("username",user.username)
        query.addDescendingOrder("createdAt")
        query.setLimit(20)
        return query.find()
    }

    private fun goToEditProfile(){
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(com.example.nightlifeapp.R.id.flContainer, EditProfileFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}