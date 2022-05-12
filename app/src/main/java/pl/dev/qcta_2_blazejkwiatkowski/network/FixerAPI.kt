package pl.dev.qcta_2_blazejkwiatkowski.network

import io.reactivex.rxjava3.core.Observable
import pl.dev.qcta_2_blazejkwiatkowski.apiData.FixerAPIDateData
import retrofit2.http.GET
import retrofit2.http.Path


interface FixerAPI {
    @GET("/fixer/{date}?apikey=8uMtXWHAMydPCG1yo8eUGvrGvT2aDDV1")
    fun getDateResponse(
        @Path("date") date: String
    ): Observable<FixerAPIDateData>
}