package com.example.nightlifeapp

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser
import com.parse.ParseFile
import java.util.*

@ParseClassName("_User")
class User : ParseUser() {

    fun getUser(): ParseUser? {
        return getParseUser(KEY_USER)
    }

    fun setUser(user: ParseUser) {
        return put(KEY_USER, user)
    }

    fun setFirstName(name: String){
        put(KEY_FIRST_NAME,name)
    }
    fun setLastName(name: String){
        put(KEY_LAST_NAME,name)
    }
    fun getFirstName(): String? {
        return getString(KEY_FIRST_NAME)
    }
    fun getLastName(): String? {
        return getString(KEY_LAST_NAME)
    }
    fun getProfilePic(): ParseFile? {
        return getParseFile(KEY_PROFILE_PICTURE)
    }
    fun setProfilePicture(parsefile: ParseFile) {
        return put(KEY_PROFILE_PICTURE, parsefile)
    }

    companion object {
        const val KEY_PROFILE_PICTURE = "profilePicture"
        const val KEY_USER = "username"
        const val KEY_FIRST_NAME = "firstname"
        const val KEY_LAST_NAME = "lastname"
    }
}