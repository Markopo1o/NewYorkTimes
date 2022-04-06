package hu.jm.newyorktimes.network

import hu.jm.newyorktimes.model.NYTimesResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TimesAPI {
    @GET("/svc/mostpopular/v2/viewed/1.json")
    suspend fun getRates(@Query("api-key") key: String) : Response<NYTimesResult>?
}