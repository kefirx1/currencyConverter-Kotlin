package pl.dev.qcta_2_blazejkwiatkowski.adapter

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import pl.dev.qcta_2_blazejkwiatkowski.ExchangeActivity
import pl.dev.qcta_2_blazejkwiatkowski.MainActivity
import pl.dev.qcta_2_blazejkwiatkowski.R
import pl.dev.qcta_2_blazejkwiatkowski.apiData.FixerAPIDateConvertedData
import java.util.*
import kotlin.collections.ArrayList

class RatesRecyclerViewAdapter(val instance: MainActivity): RecyclerView.Adapter<RatesRecyclerViewAdapter.RatesViewHolder>(){

    lateinit var fixerAPIDateConvertedData: FixerAPIDateConvertedData
    var valuesList: ArrayList<Float> = ArrayList()
    var currencyList: ArrayList<String> = ArrayList()
    var rowWithDate: ArrayList<Int> = arrayListOf(0)
    var dateList: ArrayList<String> = ArrayList()

    inner class RatesViewHolder(view: View): RecyclerView.ViewHolder(view){
        val rowRecyclerView: ConstraintLayout = view.findViewById(R.id.rowRecyclerView)
        val dateTextView: TextView = view.findViewById(R.id.dateTextView)
        val currencyTextView: TextView = view.findViewById(R.id.currencyTextView)
        val valueTextView: TextView = view.findViewById(R.id.rateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesViewHolder {
        return RatesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
        setRowDetails(
            holder = holder,
            position = position
        )
        setListener(
            holder = holder,
            position = position
        )
    }

    override fun getItemCount(): Int {
        return valuesList.size
    }


    private fun setRowDetails(holder: RatesViewHolder, position: Int){

        if(rowWithDate.contains(position)){
            holder.dateTextView.text = convertDate(dateList[position])
        }

        holder.currencyTextView.text = currencyList.elementAt(position)
        holder.valueTextView.text = valuesList[position].toBigDecimal().toPlainString()

    }

    private fun setListener(holder: RatesViewHolder, position: Int) {

        holder.rowRecyclerView.setOnClickListener{

            val intent = Intent(instance, ExchangeActivity::class.java).apply {
                putExtra("currency", currencyList[position])
                putExtra("value", valuesList[position].toBigDecimal().toPlainString())
                putExtra("base", fixerAPIDateConvertedData.base)
                putExtra("date", convertDate(dateList[position]))
            }
            instance.startActivity(intent)

        }

    }

    private fun convertDate(dateString: String): String {

        val year = dateString.substring(0,4)
        val month = dateString.substring(5, 7)
        val day = dateString.substring(8)
        return "$day-$month-$year"
    }

}
