package pl.dev.qcta_2_blazejkwiatkowski.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.dev.qcta_2_blazejkwiatkowski.apiData.FixerAPIDateConvertedData
import pl.dev.qcta_2_blazejkwiatkowski.apiData.FixerAPIDateData
import pl.dev.qcta_2_blazejkwiatkowski.apiData.Rates
import pl.dev.qcta_2_blazejkwiatkowski.models.Repository
import kotlin.reflect.full.memberProperties


class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val dataFromAPI = MutableLiveData<FixerAPIDateConvertedData>()
    val dataFromAPIResult: LiveData<FixerAPIDateConvertedData>
        get() = dataFromAPI
    private var repository: Repository = Repository()

    fun getRatesOnTheDate(dateString: String){
        repository.getRatesOnTheDate(dateString = dateString)
            .subscribeOn(Schedulers.io())
    }


    fun getFakeRatesOnTheDateRx() {
        repository.getFakeRatesOnTheDate()
            .subscribeOn(Schedulers.io())
            .subscribe { it ->

                dataFromAPI.postValue(convertToFixerAPIDateConvertedData(it))
            }
    }

    private fun Rates.asMap() : Map<String, Float> {
        val props = Rates::class.memberProperties.associateBy { it.name }
        return props.keys.associateWith { props[it]!!.get(this) as Float }
    }

    private fun convertToFixerAPIDateConvertedData(fixerAPIDateData: FixerAPIDateData): FixerAPIDateConvertedData {
        val hashMapOfRates = fixerAPIDateData.rates.asMap()

        val listOfCurrency: Set<String> = hashMapOfRates.keys
        val listOfRates: List<Float> = hashMapOfRates.values.toList()

        Log.e("TAG", listOfCurrency.toString())
        Log.e("TAG", listOfRates.toString())

        return FixerAPIDateConvertedData(
            base = fixerAPIDateData.base,
            date = fixerAPIDateData.date,
            historical = fixerAPIDateData.historical,
            currency = listOfCurrency,
            rates = listOfRates,
            success = fixerAPIDateData.success,
            timestamp = fixerAPIDateData.timestamp
        )
    }


}