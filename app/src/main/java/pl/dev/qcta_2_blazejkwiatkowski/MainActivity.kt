package pl.dev.qcta_2_blazejkwiatkowski

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import pl.dev.qcta_2_blazejkwiatkowski.adapter.RatesRecyclerViewAdapter
import pl.dev.qcta_2_blazejkwiatkowski.databinding.ActivityMainBinding
import pl.dev.qcta_2_blazejkwiatkowski.viewModels.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityMainBinding
    lateinit var ratesRecyclerViewAdapter: RatesRecyclerViewAdapter
    private lateinit var viewModel: MainActivityViewModel

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
    }

    private fun loadDataFromAPI(){
        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(MainActivityViewModel::class.java)

        viewModel.dataFromAPIResult.observe(this) {

            if(it!=null){
                ratesRecyclerViewAdapter = RatesRecyclerViewAdapter()

                ratesRecyclerViewAdapter.fixerAPIDateConvertedData = it.copy()
                ratesRecyclerViewAdapter.currencyNamesSet = it.rates.keys

                initRecyclerView()

            }else{
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }

        }

        viewModel.getFakeRatesOnTheDateRx()

    }


}
