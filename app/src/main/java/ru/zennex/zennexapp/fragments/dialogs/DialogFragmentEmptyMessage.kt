package ru.zennex.zennexapp.fragments.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.zennex.zennexapp.R

class DialogFragmentEmptyMessage : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity!!)
            .setTitle(getString(R.string.dialog_empty_message_title))
            .setPositiveButton(getString(android.R.string.yes)) { _, _ -> dismiss() }
            .create()
    }
}