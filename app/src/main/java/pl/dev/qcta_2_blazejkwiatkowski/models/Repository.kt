package pl.dev.qcta_2_blazejkwiatkowski.models

import android.util.Log
import io.reactivex.rxjava3.core.Single
import pl.dev.qcta_2_blazejkwiatkowski.apiData.FixerAPIDateData
import pl.dev.qcta_2_blazejkwiatkowski.network.FixerAPIController
import javax.inject.Singleton

@Singleton
class Repository {

    private val fixerAPIController = FixerAPIController()
    private val service = fixerAPIController.getFixerService()

    fun getRatesOnTheDate(dateString: String): Single<FixerAPIDateData> {

        Log.e("TAG", dateString)
        return service.getDateResponse(date = dateString)

    }

    fun getFakeRatesOnTheDate(): Single<FixerAPIDateData> {
        return service.getFakeDateResponse()
    }


}