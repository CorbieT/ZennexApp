package ru.zennex.zennexapp.fragments.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.zennex.zennexapp.R
import ru.zennex.zennexapp.model.ListItem
import androidx.core.util.Consumer

class DialogFragmentEditMessage(private val positiveClick: Consumer<ListItem>,
                                private val listItem: ListItem) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity!!)
            .setTitle(getString(R.string.dialog_edit_message_title))
            .setMessage(getString(R.string.dialog_edit_message))
            .setPositiveButton(getString(android.R.string.yes)) { _, _ ->
                positiveClick.accept(listItem)
                dismiss()}
            .setNegativeButton(getString(android.R.string.cancel)) { _, _ -> dismiss() }
            .create()
    }

}