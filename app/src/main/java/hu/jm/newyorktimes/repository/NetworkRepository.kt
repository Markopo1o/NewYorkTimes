package hu.jm.newyorktimes.repository

import hu.jm.newyorktimes.datasource.network.TimesNetworkDataSource
import hu.jm.newyorktimes.util.NetworkResponse

class NetworkRepository(private val timesNetworkDataSource: TimesNetworkDataSource){
    suspend fun getNewsInRepositoty() : NetworkResponse<Any> {
        return timesNetworkDataSource.getNewsInDataSource()
    }
}