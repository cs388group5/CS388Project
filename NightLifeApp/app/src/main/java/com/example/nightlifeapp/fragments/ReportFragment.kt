package com.example.nightlifeapp.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.app.TimePickerDialog
import java.util.*
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.nightlifeapp.Crime
import com.example.nightlifeapp.R
import com.parse.ParseUser

class ReportFragment : Fragment() {

    lateinit var btnTime: Button
    lateinit var btnDate: Button
    lateinit var btnSubmit: Button

    lateinit var tvTime: TextView
    lateinit var tvDate: TextView

    lateinit var etCrimeType: EditText
    lateinit var etDescription: EditText
    lateinit var etLocation: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set the time button
        btnTime = view.findViewById(R.id.btnTime)
        btnDate = view.findViewById(R.id.btnDate)
        btnSubmit = view.findViewById(R.id.btnSubmit)

        tvTime = view.findViewById(R.id.tvTime)
        tvDate = view.findViewById(R.id.tvDate)

        etCrimeType = view.findViewById(R.id.btnLogout)
        etDescription = view.findViewById(R.id.etCrimeDescription)
        etLocation = view.findViewById(R.id.etLocation)

        btnTime.setOnClickListener{

            // the instance of our calendar.
            val c = Calendar.getInstance()

            // on below line we are getting our hour, minute.
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            // on below line we are initializing
            // our Time Picker Dialog
            val timePickerDialog = TimePickerDialog(
                this.context,
                { view, hourOfDay, minute ->
                    // on below line we are setting selected
                    // time in our text view.
                    tvTime.setText("$hourOfDay:$minute")
                },
                hour,
                minute,
                false
            )
            // at last we are calling show to
            // display our time picker dialog.
            timePickerDialog.show()
        }

        btnDate.setOnClickListener{

            // on below line we are getting
            // the instance of our calendar.
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our text view.
                    tvDate.text =
                        (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog.show()
        }

        btnSubmit.setOnClickListener {

            val crimeType = view.findViewById<EditText>(R.id.btnLogout).text.toString()
            val description = view.findViewById<EditText>(R.id.etCrimeDescription).text.toString()
            val location = view.findViewById<EditText>(R.id.etLocation).text.toString()
            val date = view.findViewById<TextView>(R.id.tvDate).text.toString()
            val time = view.findViewById<TextView>(R.id.tvTime).text.toString()
            val user = ParseUser.getCurrentUser()

            if(user!=null){
                createReport(crimeType,description,location,date,time,user)
            }else{
                Toast.makeText(this.context,"User not logged in, can not report", Toast.LENGTH_SHORT).show()
            }

        }



    }

    private fun createReport(crimeType: String, description: String, location:String, date: String, time: String, user: ParseUser){

        val crime = Crime()

        crime.setCrimeType(crimeType)
        crime.setDescription(description)
        crime.setLocation(location)
        crime.setDate(date)
        crime.setTime(time)
        crime.setUser(user)

        crime.saveInBackground { exception->
            if(exception!=null){
                Log.e(TAG,"error creating report")
                exception.printStackTrace()
            } else{
                Log.i(TAG,"successfully created report")
                //reset all fields
                etCrimeType.text.clear()
                etDescription.text.clear()
                etLocation.text.clear()
                tvDate.text="Enter Date"
                tvTime.text="Enter Time"

            }
        }
    }

    companion object{
        val TAG = "ReportFragment"
    }
}