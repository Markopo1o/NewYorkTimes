package hu.jm.newyorktimes.datasource.network

import hu.jm.newyorktimes.BuildConfig
import hu.jm.newyorktimes.network.TimesAPI
import hu.jm.newyorktimes.util.NetworkErrorResult
import hu.jm.newyorktimes.util.NetworkResponse
import hu.jm.newyorktimes.util.NetworkResult
import java.lang.Exception

class TimesNetworkDataSource(private val timesAPI: TimesAPI) {
    suspend fun getNewsInDataSource(): NetworkResponse<Any> {

        try {

            val response = timesAPI.getRates(BuildConfig.TIMES_API_KEY)

            response?.let {

                return NetworkResult(it.body()!!)
            }

            return NetworkErrorResult(Exception("No data"))
        } catch (e: Exception) {

            return NetworkErrorResult(e)
        }
    }
}