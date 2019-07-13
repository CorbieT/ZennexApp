package ru.zennex.zennexapp.model.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import io.reactivex.Observable
import ru.zennex.zennexapp.model.ListItem
import ru.zennex.zennexapp.model.tableName

@Dao
interface ListItemDAO {

    @Query("SELECT * FROM $tableName")
    fun getAll(): Observable<List<ListItem>>

    @Query("SELECT * FROM $tableName WHERE id = :id")
    fun getById(id: Long): ListItem

    @Insert(onConflict = REPLACE)
    fun insert(listItem: ListItem)

    @Update
    fun update(listItem: ListItem)

    @Delete
    fun delete(listItem: ListItem)

}