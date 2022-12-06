package com.example.nightlifeapp.fragments

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.nightlifeapp.R

class FormDialogFragment : DialogFragment() {

    internal lateinit var listener: FormDialogListener
    lateinit var etFirstname : EditText
    lateinit var etLastName: EditText
    lateinit var etPhoneNumber: EditText
    lateinit var spinner: Spinner
    interface FormDialogListener {
        fun onDialogPositiveClick(x: List<String>)
        fun onDialogNegativeClick(x: List<String>)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;
            // Inflate and set the layout for the dialog
            var dialogLayout = inflater.inflate(R.layout.fragment_form_dialog,null)
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(dialogLayout)
                // Add title
                .setTitle(R.string.formTitle)
                // Add action buttons
                .setPositiveButton("Submit",
                    DialogInterface.OnClickListener { dialog, id ->
                        // Send positive event back to host
                        etFirstname = dialogLayout.findViewById(R.id.etFirstname)
                        etLastName = dialogLayout.findViewById(R.id.etLastname)
                        etPhoneNumber = dialogLayout.findViewById(R.id.etPhoneNumber)
                        spinner = dialogLayout.findViewById(R.id.spinner)

                        var arr = mutableListOf<String>()

                        if (etFirstname.text.toString().length != 0){
                            arr.add(etFirstname.text.toString())
                        }
                        if (etLastName.text.toString().length != 0){
                            arr.add(etLastName.text.toString())
                        }
                        if (etPhoneNumber.text.toString().length != 0){
                            arr.add(etPhoneNumber.text.toString())
                        }
                        if (spinner.selectedItem.toString() != ""){
                            arr.add(spinner.selectedItem.toString())
                        }

                        listener.onDialogPositiveClick(arr)

                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.onDialogNegativeClick(listOf())
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener =  targetFragment as FormDialogListener
        } catch (e: ClassCastException){
            throw ClassCastException((context.toString() + " must implement NoticeDialogListener"))
        }
    }
}