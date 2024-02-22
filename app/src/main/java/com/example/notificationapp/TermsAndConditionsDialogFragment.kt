package com.example.notificationapp

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.notificationapp.R

class TermsAndConditionsDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Inflate the layout for the dialog
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.terms_and_conditions_dialog_fragment, null)

        // Build the dialog
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)
            // Optionally set positive and negative buttons


        // Create the AlertDialog object and return it
        return builder.create()
    }
}
