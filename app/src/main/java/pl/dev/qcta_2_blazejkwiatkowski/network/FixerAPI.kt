package pl.dev.qcta_2_blazejkwiatkowski.network

import io.reactivex.rxjava3.core.Single
import pl.dev.qcta_2_blazejkwiatkowski.apiData.FixerAPIDateData
import retrofit2.http.GET
import retrofit2.http.Path


interface FixerAPI {
    @GET("/fixer/{date}?apikey=8uMtXWHAMydPCG1yo8eUGvrGvT2aDDV1")
    fun getDateResponse(
        @Path("date") date: String
    ): Single<FixerAPIDateData>

    @GET("/v1/732a6a0b-f079-4ff9-a491-b68efe9fc985")
    fun getFakeDateResponse(): Single<FixerAPIDateData>

}