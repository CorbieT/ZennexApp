package ru.zennex.zennexapp.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.zennex.zennexapp.model.ListItem


//https://stackoverflow.com/questions/44322178/room-schema-export-directory-is-not-provided-to-the-annotation-processor-so-we
@Database(entities = [ListItem::class], version = 1, exportSchema = false)
abstract class ListItemDataBase : RoomDatabase(){

    abstract fun listItemDAO(): ListItemDAO

    companion object {
        /**
         * @Volatile - при записи в это поле, оно сразу становится видимым для других потоков
        */
        @Volatile
        private var INSTANCE: ListItemDataBase? = null

        fun getDataBase(context: Context): ListItemDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance =  Room.databaseBuilder(context.applicationContext,
                    ListItemDataBase::class.java,
                    "listitems.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}