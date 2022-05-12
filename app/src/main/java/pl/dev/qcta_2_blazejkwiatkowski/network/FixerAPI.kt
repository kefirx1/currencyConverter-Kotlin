package pl.dev.qcta_2_blazejkwiatkowski.network

import io.reactivex.rxjava3.core.Observable
import pl.dev.qcta_2_blazejkwiatkowski.apiData.FixerAPIDateData
import retrofit2.http.GET
import retrofit2.http.Path


interface FixerAPI {
    @GET("/fixer/{date}?apikey=5UEVByj8QaXTckdq4vE7WN2z8h9jA1IH")
    fun getDateResponse(
        @Path("date") date: String
    ): Observable<FixerAPIDateData>
}