package ru.zennex.zennexapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val tableName = "listitem"

@Entity
data class ListItem(@PrimaryKey(autoGenerate = true) val id: Long = 0,
                    @ColumnInfo var description: String,
                    @ColumnInfo var checked: Boolean = false)

data class ListItems(val total: Int, val last: String, val listItems: List<ListItem>)