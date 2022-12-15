package com.example.nightlifeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.nightlifeapp.R
import java.util.Calendar
import java.util.Date

class FilterDialog : DialogFragment() {

    var name: String? = null
    var type: String? = null
    var time: Date? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for the dialog
        val view = inflater.inflate(R.layout.fragment_filter_dialog, container, false)

        // Get the filter criteria from the arguments, if any
        val arguments = this.arguments
        if (arguments != null) {
            name = arguments.getString("name")
            type = arguments.getString("type")
            time = arguments.getSerializable("time") as Date?
        }

        // Find the "Apply" button and set an onClickListener on it
        val btApply = view.findViewById<Button>(R.id.btApply)
        btApply.setOnClickListener {
            // Get the filter criteria from the dialog's input fields
            name = view.findViewById<EditText>(R.id.etName).text.toString()
            type = view.findViewById<EditText>(R.id.etType).text.toString()

            // Get the date from the DatePicker view
            val datePicker = view.findViewById<DatePicker>(R.id.dpTime)
            val year = datePicker.year
            val month = datePicker.month
            val day = datePicker.dayOfMonth

            // Close the dialog
            dismiss()
        }

        // Return the inflated layout
        return view
    }
}
