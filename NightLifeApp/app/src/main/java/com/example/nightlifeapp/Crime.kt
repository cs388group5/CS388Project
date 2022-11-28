package com.example.parstagram

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser
import java.util.*

@ParseClassName("Crime")
class Crime : ParseObject() {

    fun getDescription(): String? {
        return getString(KEY_DESCRIPTION)

    }
    fun setDescription(description:String){
        put(KEY_DESCRIPTION,description)
    }

    fun getCrimeType(): String? {
        return getString(KEY_CRIME_TYPE)
    }
    fun setCrimeType(crimeType:String){
        put(KEY_CRIME_TYPE,crimeType)
    }

    fun getLocation(): String? {
        return getString(KEY_LOCATION)

    }
    fun setLocation(location:String){
        put(KEY_LOCATION,location)
    }
    fun getDate(): Date? {
        return  getDate(KEY_DATE)


    }
    fun setDate(date:Date){
        put(KEY_DATE,date)
    }



    fun getUser(): ParseUser?{
        return getParseUser(KEY_USER)
    }

    fun setUser(user:ParseUser){
        put(KEY_USER,user)
    }


    companion object{
        const val KEY_DESCRIPTION = "description"
        const val KEY_CRIME_TYPE = "crime_type"
        const val KEY_LOCATION = "location"
        const val KEY_DATE = "date"
        const val KEY_USER = "user_id"

    }


}