package ru.zennex.zennexapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.viewModelScope
import io.reactivex.Observable
import io.reactivex.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.zennex.zennexapp.model.ListItem
import ru.zennex.zennexapp.model.db.ListItemDataBase
import ru.zennex.zennexapp.repositories.ListItemRepository

class ListItemViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

    private val repository: ListItemRepository = ListItemRepository(application)

    val allListItems: Observable<List<ListItem>>

    init {
        allListItems = repository.allListItems
    }

    fun insert(listItem: ListItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(listItem)
    }

    fun update(listItem: ListItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(listItem)
    }

    fun delete(listItem: ListItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(listItem)
    }
}