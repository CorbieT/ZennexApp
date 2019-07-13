package ru.zennex.zennexapp.repositories

import androidx.annotation.WorkerThread
import io.reactivex.Observable
import ru.zennex.zennexapp.model.ListItem
import ru.zennex.zennexapp.model.db.ListItemDAO

class ListItemRepository(private val listItemDAO: ListItemDAO) {
    val allListItems: Observable<List<ListItem>> = listItemDAO.getAll()

    @WorkerThread
    fun insert(listItem: ListItem) {
        listItemDAO.insert(listItem)
    }

    @WorkerThread
    fun update(listItem: ListItem) {
        listItemDAO.update(listItem)
    }

    @WorkerThread
    fun delete(listItem: ListItem) {
        listItemDAO.delete(listItem)
    }
}