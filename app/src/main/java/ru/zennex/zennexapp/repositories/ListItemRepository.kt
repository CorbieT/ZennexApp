package ru.zennex.zennexapp.repositories

import android.app.Application
import androidx.annotation.WorkerThread
import io.reactivex.Observable
import ru.zennex.zennexapp.model.ListItem
import ru.zennex.zennexapp.model.db.ListItemDAO
import ru.zennex.zennexapp.model.db.ListItemDataBase

class ListItemRepository(application: Application) {
    private val listItemDAO: ListItemDAO = ListItemDataBase.getDataBase(application).listItemDAO()
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