package hu.jm.newyorktimes.ui.list

import android.app.Application
import androidx.lifecycle.*
import hu.jm.newyorktimes.datasource.disk.NewsDatabase
import hu.jm.newyorktimes.model.NYTimesResult
import hu.jm.newyorktimes.model.News
import hu.jm.newyorktimes.repository.DiskRepository
import hu.jm.newyorktimes.repository.NetworkRepository
import hu.jm.newyorktimes.util.NetworkErrorResult
import hu.jm.newyorktimes.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : AndroidViewModel(application){
    private var networkRepository: NetworkRepository = NetworkRepository()
    private val result = MutableLiveData<ListViewState>()
    fun getLiveData() = result
    fun getNewsInViewModel(){
        result.value = InProgress
        viewModelScope.launch(Dispatchers.IO){
            val response = networkRepository.getNewsInRepositoty()
            when(response){
                is NetworkResult -> {
                    val newsResult = response.result as NYTimesResult
                    result.postValue(ResponseSuccess(newsResult))
                }
                is NetworkErrorResult -> {
                    result.postValue(ResponseError(response.errorMessage.message!!))
                }
            }
        }
    }
    private val newsDao = NewsDatabase.getDatabase(application).newsDao()
    private val diskRepository: DiskRepository = DiskRepository(newsDao)
    val readAllDataInViewModel: LiveData<List<News>> = diskRepository.readAllDataInRepository
    fun deleteAllUsers(){
        viewModelScope.launch(Dispatchers.IO) {
            diskRepository.deleteAllNewsInRepository()
        }
    }
    fun addNewsInViewModel(news: News){
        viewModelScope.launch(Dispatchers.IO) {
            diskRepository.addUserInRepository(news)
        }
    }
    fun searchDatabase(searchQuery: String): LiveData<List<News>> {
        return diskRepository.searchDatabase(searchQuery).asLiveData()
    }
}