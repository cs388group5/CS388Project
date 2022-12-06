package com.example.nightlifeapp

import com.parse.ParseClassName
import com.parse.ParseObject

// Contact name
// Contact phone number

@ParseClassName("emergency_contacts")
class EmergencyContact: ParseObject() {
    fun setFirstName(name: String){
        put(KEY_FIRST_NAME,name)
    }
    fun setLastName(name: String){
        put(KEY_LAST_NAME,name)
    }
    fun setRelationship(name: String){
        put(KEY_RELATIONSHIP,name)
    }
    fun setPhoneNumber(name: String){
        put(KEY_PHONE_NUMBER,name)
    }

    fun getName(): String?{
        return getString(KEY_FIRST_NAME) + ' '+ getString(KEY_LAST_NAME)
    }

    fun getRelationship(): String?{
        return getString(KEY_RELATIONSHIP)
    }

    fun getPhoneNumber(): String? {
        return getString(KEY_PHONE_NUMBER)
    }

    companion object {
        const val KEY_FIRST_NAME = "first_name"
        const val KEY_LAST_NAME = "last_name"
        const val KEY_PHONE_NUMBER= "phone_number"
        const val KEY_RELATIONSHIP= "relationship"
    }
}