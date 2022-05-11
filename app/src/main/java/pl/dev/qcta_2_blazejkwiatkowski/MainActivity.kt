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

    lateinit var binding: ActivityMainBinding
    lateinit var ratesRecyclerViewAdapter: RatesRecyclerViewAdapter
    private lateinit var viewModel: MainActivityViewModel

    private var dayCounter = -1
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (DeviceInfo.checkInternetConnection(applicationContext)) {
            loadDataFromAPI()
        } else {
            Toast.makeText(
                this,
                "Brak połączenia z internetem",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun initRecyclerView() {
        binding.mainRecyclerView.apply {
            adapter = ratesRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        binding.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->

            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                if (count < 20) {
                    count++
                    binding.progressBar.visibility = View.VISIBLE
                    if (DeviceInfo.checkInternetConnection(applicationContext)) {
                        ratesRecyclerViewAdapter.rowWithDate.add(ratesRecyclerViewAdapter.list.size)
                        viewModel.getFakeRatesOnTheDateRx()
                        dayCounter--
//                    viewModel.getRatesOnTheDateRx(getDataString(dayCounter))
                    } else {
                        Toast.makeText(
                            this,
                            "Brak połączenia z internetem",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Starasz się załadować za dużo danych!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

    }

    private fun loadDataFromAPI(){
        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(MainActivityViewModel::class.java)

        ratesRecyclerViewAdapter = RatesRecyclerViewAdapter()

        viewModel.dataFromAPIResult.observe(this) {

            if(it!=null){

                ratesRecyclerViewAdapter.fixerAPIDateConvertedData = it

                it.currency.forEach{ currency ->
                    ratesRecyclerViewAdapter.currencyList.add(currency)
                }

                it.rates.forEach{ rate ->
                    ratesRecyclerViewAdapter.list.add(rate)
                }


                initRecyclerView()
                binding.progressBar.visibility = View.INVISIBLE

            }else{
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }

        }

        viewModel.getFakeRatesOnTheDateRx()
//        viewModel.getRatesOnTheDateRx(getDataString(dayValue = dayCounter))
    }


    private fun getDataString(dayValue: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, dayValue)

        val dayInt = calendar.get(Calendar.DAY_OF_MONTH)
        val monthInt = calendar.get(Calendar.MONTH)+1
        val year = calendar.get(Calendar.YEAR)
        var dayString = dayInt.toString()
        var monthString = monthInt.toString()

        if(dayInt<10){
            dayString = "0$dayInt"
        }
        if(monthInt<10){
            monthString = "0$monthInt"
        }


        return "$year-$monthString-$dayString"
    }

}
