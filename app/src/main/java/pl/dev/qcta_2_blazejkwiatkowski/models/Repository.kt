package pl.dev.qcta_2_blazejkwiatkowski.models

import io.reactivex.rxjava3.core.Observable
import pl.dev.qcta_2_blazejkwiatkowski.apiData.FixerAPIDateData
import pl.dev.qcta_2_blazejkwiatkowski.network.FixerAPIController

class Repository {

    private val fixerAPIController = FixerAPIController()
    private val service = fixerAPIController.getFixerService()

    fun getRatesOnTheDate(dateString: String): Observable<FixerAPIDateData> {
        return service.getDateResponse(date = dateString)

    }

}