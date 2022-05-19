package hu.jm.newyorktimes.datasource.disk

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.jm.newyorktimes.model.News
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNewsInNewsDao(news: News)

    @Query("DELETE FROM news_table")
    suspend fun deleteAllNewsInNewsDao()

    @Query("SELECT * FROM news_table ORDER BY id ASC")
    fun readAllDataInNewsDao(): LiveData<List<News>>

    @Query("SELECT * FROM news_table WHERE title LIKE " +
            ":searchQuery OR author LIKE :searchQuery OR updated LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<News>>
}