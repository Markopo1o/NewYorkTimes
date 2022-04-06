package hu.jm.newyorktimes.datasource.network

import hu.jm.newyorktimes.BuildConfig
import hu.jm.newyorktimes.network.RetrofitClient
import hu.jm.newyorktimes.util.NetworkErrorResult
import hu.jm.newyorktimes.util.NetworkResponse
import hu.jm.newyorktimes.util.NetworkResult
import java.lang.Exception

object TimesNetworkDataSource {
    suspend fun getNewsInDataSource(): NetworkResponse<Any> {
        try {
            val response = RetrofitClient.apiInterface.getRates(BuildConfig.TIMES_API_KEY)
            response?.let {
                return NetworkResult(it.body()!!)
            }
            return NetworkErrorResult(Exception("No data"))
        } catch (e: Exception) {
            return NetworkErrorResult(e)
        }
    }
}