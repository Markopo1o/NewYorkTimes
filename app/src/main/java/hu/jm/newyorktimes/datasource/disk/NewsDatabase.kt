package hu.jm.newyorktimes.datasource.disk

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.jm.newyorktimes.converters.Converters
import hu.jm.newyorktimes.model.News

@Database(entities = [News::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun newsDao(): NewsDao
}