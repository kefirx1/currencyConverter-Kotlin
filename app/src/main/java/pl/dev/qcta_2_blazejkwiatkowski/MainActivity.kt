package pl.dev.qcta_2_blazejkwiatkowski

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.json.JSONArray
import org.json.JSONException
import pl.dev.qcta_2_blazejkwiatkowski.adapter.RatesRecyclerViewAdapter
import pl.dev.qcta_2_blazejkwiatkowski.databinding.ActivityMainBinding
import pl.dev.qcta_2_blazejkwiatkowski.viewModels.MainActivityViewModel


class MainActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityMainBinding
    lateinit var ratesRecyclerViewAdapter: RatesRecyclerViewAdapter
    private lateinit var viewModel: MainActivityViewModel

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        loadDataFromAPI()
    }

    private fun initRecyclerView() {
        viewBinding.mainRecyclerView.apply {
            adapter = ratesRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
//        viewBinding.mainRecyclerView.setHasFixedSize(true)

        viewBinding.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                count++
                viewBinding.progressBar.visibility = View.VISIBLE

                if (count < 20) {
                    viewModel.getFakeRatesOnTheDateRx()
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
                viewBinding.progressBar.visibility = View.INVISIBLE

            }else{
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }

        }

        viewModel.getFakeRatesOnTheDateRx()

    }


}
