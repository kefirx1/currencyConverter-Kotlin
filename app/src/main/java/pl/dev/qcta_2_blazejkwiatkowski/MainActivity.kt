package pl.dev.qcta_2_blazejkwiatkowski

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import pl.dev.qcta_2_blazejkwiatkowski.adapter.RatesRecyclerViewAdapter
import pl.dev.qcta_2_blazejkwiatkowski.databinding.ActivityMainBinding
import pl.dev.qcta_2_blazejkwiatkowski.viewModels.MainActivityViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var ratesRecyclerViewAdapter: RatesRecyclerViewAdapter
    private lateinit var viewModel: MainActivityViewModel

    private var previousDaysCounter = 0
    private var loadCounter = 0
    private val internetConnectionErrorMessage = "Brak połączenia z internetem"
    private val apiConnectionErrorMessage = "Błąd połączenia z bazą danych"
    private val tooMuchInfoErrorMessage = "Starasz się załadować za dużo danych!!"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkStatus()
    }

    private fun checkStatus() {
        if (DeviceInfo.checkInternetConnection(applicationContext)) {

            binding.constraintLayout.setOnClickListener(null)
            val headerText = "EURO - PRZELICZNIK"
            binding.headerTextView.text = headerText

            loadDataFromAPI()
        } else {
            Toast.makeText(
                this,
                internetConnectionErrorMessage,
                Toast.LENGTH_SHORT
            ).show()
            val headerTextError = "Kliknij aby odświeżyć"
            binding.headerTextView.text = headerTextError
            binding.constraintMain.setOnClickListener {
                checkStatus()
            }
        }
    }

    private fun initRecyclerView() {
        binding.mainRecyclerView.apply {
            adapter = ratesRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        binding.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->

            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                if (loadCounter <= 20) {
                    loadCounter++
                    binding.progressBar.visibility = View.VISIBLE
                    ratesRecyclerViewAdapter.rowWithDate.add(ratesRecyclerViewAdapter.valuesList.size)
                    if (DeviceInfo.checkInternetConnection(applicationContext)) {
                        previousDaysCounter--
                        viewModel.getRatesOnTheDateRx(getDataString(previousDaysCounter))
                    } else {
                        Toast.makeText(
                            this,
                            internetConnectionErrorMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        tooMuchInfoErrorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

    }

    private fun loadDataFromAPI() {

        viewModel = ViewModelProvider(this)
            .get(MainActivityViewModel::class.java)

        ratesRecyclerViewAdapter = RatesRecyclerViewAdapter(instance = this)

        viewModel.dataFromAPIResult.observe(this) {

            if (it != null) {

                ratesRecyclerViewAdapter.fixerAPIDateConvertedData = it

                it.currency.forEach { currency ->
                    ratesRecyclerViewAdapter.currencyList.add(currency)
                }

                it.rates.forEach { rate ->
                    ratesRecyclerViewAdapter.valuesList.add(rate)
                }

                it.dates.forEach { date ->
                    ratesRecyclerViewAdapter.dateList.add(date)
                }

                initRecyclerView()
                binding.progressBar.visibility = View.INVISIBLE

            } else {
                Toast.makeText(
                    this,
                    apiConnectionErrorMessage,
                    Toast.LENGTH_SHORT
                ).show()
                binding.headerTextView.text = viewModel.errorMessage
            }
        }
        viewModel.getRatesOnTheDateRx(getDataString(dayValue = previousDaysCounter))
    }


    private fun getDataString(dayValue: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, dayValue)

        val dayInt = calendar.get(Calendar.DAY_OF_MONTH)
        val monthInt = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        var dayString = dayInt.toString()
        var monthString = monthInt.toString()

        if (dayInt < 10) {
            dayString = "0$dayInt"
        }
        if (monthInt < 10) {
            monthString = "0$monthInt"
        }

        return "$year-$monthString-$dayString"
    }

}
