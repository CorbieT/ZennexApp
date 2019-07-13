package ru.zennex.zennexapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.util.Consumer
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_edit_list.*
import ru.zennex.zennexapp.R
import ru.zennex.zennexapp.fragments.dialogs.DialogFragmentEditMessage
import ru.zennex.zennexapp.fragments.dialogs.DialogFragmentEmptyMessage
import ru.zennex.zennexapp.interfaces.OnBackPressedListener
import ru.zennex.zennexapp.model.ListItem
import ru.zennex.zennexapp.viewModels.ListItemViewModel

class EditListFragment(private var listItem: ListItem? = null) : Fragment(), OnBackPressedListener {

    private lateinit var listItemViewModel: ListItemViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listItemViewModel = ViewModelProviders.of(activity!!).get(ListItemViewModel::class.java)

        listItem?.let {
            editText.setText(it.description)
        }

        buttonDone.setOnClickListener {
            if (editText.text.toString().isEmpty()) {
                fragmentManager?.let { DialogFragmentEmptyMessage().show(it, null) }
                return@setOnClickListener
            }
            if (listItem != null) {
                listItem?.description = editText.text.toString()
                listItemViewModel.update(listItem!!)
                Toast.makeText(activity!!, getString(R.string.item_changed_toast_message), Toast.LENGTH_SHORT).show()
            } else {
                listItemViewModel.insert(ListItem(description = editText.text.toString()))
                Toast.makeText(activity!!, getString(R.string.item_added_toast_message), Toast.LENGTH_SHORT).show()
            }
            activity!!.supportFragmentManager.popBackStack()
        }

        buttonRevert.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }
    }

    override fun onBackPressed() {
        if (editText.text.toString() != listItem?.description) {
            if (listItem != null) {
                fragmentManager?.let {
                    DialogFragmentEditMessage(Consumer { t ->
                        t.description = editText.text.toString()
                        listItemViewModel.update(t)
                        activity!!.supportFragmentManager.popBackStack()
                    }, listItem!!)
                        .show(it, null)
                }
            }
        } else {
            activity!!.supportFragmentManager.popBackStack()
        }
    }

}