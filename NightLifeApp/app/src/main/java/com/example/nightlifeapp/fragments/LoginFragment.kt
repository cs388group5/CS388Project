package com.example.nightlifeapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.nightlifeapp.MainActivity
import com.example.nightlifeapp.R
import com.parse.ParseUser
import com.example.nightlifeapp.fragments.HomeFragment


class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
        if (ParseUser.getCurrentUser() != null){
            goToHomePage()
        }

        view.findViewById<Button>(R.id.btnSignup).setOnClickListener {
            val username = view.findViewById<EditText>(R.id.etUsername).text.toString()
            val password = view.findViewById<EditText>(R.id.etPassword).text.toString()

            loginUser(username, password)
        }
        view.findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val username = view.findViewById<EditText>(R.id.etUsername).text.toString()
            val password = view.findViewById<EditText>(R.id.etPassword).text.toString()

            signUpUser(username, password)
        }

    }

    private fun signUpUser(username: String, password: String){
        val user = ParseUser()

// Set fields for the user to be created
        user.setUsername(username)
        user.setPassword(password)

        user.signUpInBackground { e ->
            if (e == null) {
                goToHomePage()
                Toast.makeText(requireContext(), "You have registered successfully!", Toast.LENGTH_SHORT).show()
            } else {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Sign up was not successful.", Toast.LENGTH_SHORT).show()
                // Sign up didn't succeed. Look at the ParseException
                // to figure out what went wrong
            }
        }
    }

    private fun loginUser(username: String, password: String){
        ParseUser.logInInBackground(username, password, ({ user, e ->
            if (user != null) {
                goToHomePage()
            } else {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Error logging in", Toast.LENGTH_SHORT).show()
                // Signup failed.  Look at the ParseException to see what happened.
            }})
        )
    }

    private fun goToHomePage(){

            parentFragmentManager.beginTransaction().replace(R.id.flContainer, HomeFragment()).commit()

    }


}