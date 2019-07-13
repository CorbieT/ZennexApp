package ru.zennex.zennexapp.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.util.Consumer
import kotlinx.android.synthetic.main.list_list_item.view.*
import ru.zennex.zennexapp.R
import ru.zennex.zennexapp.extensions.inflate
import ru.zennex.zennexapp.model.ListItem

class ListAdapter(private var listItems: MutableList<ListItem>,
    val checkBoxClick: Consumer<ListItem>,
    private val itemClick: Consumer<ListItem>,
    private val longClick: Consumer<ListItem>): RecyclerView.Adapter<ListAdapter.ListHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ListHolder {
        return ListHolder(p0.inflate(R.layout.list_list_item))
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.bindListItem(listItems[position])
    }

    fun update(listItems: MutableList<ListItem>) {
        //TODO implement DiffUtil
        this.listItems.clear()
        this.listItems.addAll(listItems)
        notifyDataSetChanged()
    }

    inner class ListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val checkBox = itemView.checkboxList
        private val descriptionTextViewList = itemView.descriptionTextViewList
        private val imageButtonList = itemView.imageButtonList

        fun bindListItem(listItem: ListItem) {
            checkBox.isChecked = listItem.checked
            descriptionTextViewList.text = listItem.description
            setImageButtonList(listItem.checked)

            checkBox.setOnClickListener {
                listItem.checked = checkBox.isChecked
                setImageButtonList(listItem.checked)
                checkBoxClick.accept(listItem)
            }

            itemView.setOnClickListener {
                itemClick.accept(listItem)
            }

            itemView.setOnLongClickListener {
                val popupMenu = PopupMenu(itemView.context, itemView)
                popupMenu.inflate(R.menu.pop_up_list_item)
                popupMenu.setOnMenuItemClickListener{
                    when (it?.itemId) {
                        R.id.edit_menu  -> itemClick.accept(listItem)
                        R.id.delete_menu  -> longClick.accept(listItem)
                        else -> return@setOnMenuItemClickListener false
                    }
                    return@setOnMenuItemClickListener true
                }
                popupMenu.show()
                return@setOnLongClickListener true
            }
        }

        private fun setImageButtonList(isChecked: Boolean) {
            if (isChecked) imageButtonList.setImageDrawable(itemView.context.getDrawable(R.drawable.icon_access))
            else imageButtonList.setImageDrawable(itemView.context.getDrawable(R.drawable.icon_block))
        }
    }

}
