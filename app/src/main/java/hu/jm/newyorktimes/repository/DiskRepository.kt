package hu.jm.newyorktimes.repository

import androidx.lifecycle.LiveData
import hu.jm.newyorktimes.datasource.disk.NewsDao
import hu.jm.newyorktimes.model.News
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiskRepository @Inject constructor(
    private val newsDao: NewsDao
    ) {

    val readAllDataInRepository: LiveData<List<News>> = newsDao.readAllDataInNewsDao()
    suspend fun addUserInRepository(news: News){

        newsDao.addNewsInNewsDao(news)
    }

    suspend fun deleteAllNewsInRepository(){

        newsDao.deleteAllNewsInNewsDao()
    }

    fun searchDatabase(searchQuery: String): Flow<List<News>> {

        return newsDao.searchDatabase(searchQuery)
    }
}
