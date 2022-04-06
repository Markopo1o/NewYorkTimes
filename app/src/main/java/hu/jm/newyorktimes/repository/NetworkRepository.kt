package hu.jm.newyorktimes.repository

import hu.jm.newyorktimes.datasource.network.TimesNetworkDataSource
import hu.jm.newyorktimes.util.NetworkResponse

class NetworkRepository {
    suspend fun getNewsInRepositoty() : NetworkResponse<Any> {
        return TimesNetworkDataSource.getNewsInDataSource()
    }
}