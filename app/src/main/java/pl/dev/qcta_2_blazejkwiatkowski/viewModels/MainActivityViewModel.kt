package pl.dev.qcta_2_blazejkwiatkowski.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.dev.qcta_2_blazejkwiatkowski.apiData.FixerAPIDateConvertedData
import pl.dev.qcta_2_blazejkwiatkowski.apiData.FixerAPIDateData
import pl.dev.qcta_2_blazejkwiatkowski.apiData.Rates
import pl.dev.qcta_2_blazejkwiatkowski.models.Repository
import kotlin.reflect.full.memberProperties


class MainActivityViewModel() : ViewModel() {

    var errorMessage = ""
    private val dataFromAPI = MutableLiveData<FixerAPIDateConvertedData?>()
    val dataFromAPIResult: LiveData<FixerAPIDateConvertedData?>
        get() = dataFromAPI
    private var repository: Repository = Repository()

    fun getRatesOnTheDateRx(dateString: String) {
        repository.getRatesOnTheDate(dateString = dateString)
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<FixerAPIDateData> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: FixerAPIDateData) {
                    dataFromAPI.postValue(convertToFixerAPIDateConvertedData(t))
                }

                override fun onError(e: Throwable) {
                    dataFromAPI.postValue(null)
                    errorMessage = "Spróbuj ponownie później"
                }

                override fun onComplete() {
                }

            })
    }

    private fun Rates.asMap() : Map<String, Float> {
        val props = Rates::class.memberProperties.associateBy { it.name }
        return props.keys.associateWith { props[it]!!.get(this) as Float }
    }

    fun convertToFixerAPIDateConvertedData(fixerAPIDateData: FixerAPIDateData): FixerAPIDateConvertedData {

        val hashMapOfRates = fixerAPIDateData.rates.asMap()

        val listOfCurrency: Set<String> = hashMapOfRates.keys
        val listOfRates: List<Float> = hashMapOfRates.values.toList()
        val listOfDates: ArrayList<String> = ArrayList()

        for(i in listOfCurrency.indices){
            listOfDates.add(fixerAPIDateData.date)
        }

        return FixerAPIDateConvertedData(
            base = fixerAPIDateData.base,
            dates = listOfDates,
            historical = fixerAPIDateData.historical,
            currency = listOfCurrency,
            rates = listOfRates,
            success = fixerAPIDateData.success,
            timestamp = fixerAPIDateData.timestamp
        )
    }


}