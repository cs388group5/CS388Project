package com.example.nightlifeapp

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser
import com.parse.ParseFile
import java.util.*

@ParseClassName("User")
class User : ParseObject() {

    fun getUser(): ParseUser? {
        return getParseUser(KEY_USER)
    }

    fun setUser(user: ParseUser) {
        return put(KEY_USER, user)
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
    }
}