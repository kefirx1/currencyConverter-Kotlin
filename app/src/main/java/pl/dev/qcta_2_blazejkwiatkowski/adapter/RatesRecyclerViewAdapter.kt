package pl.dev.qcta_2_blazejkwiatkowski.adapter

import android.icu.text.SimpleDateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.dev.qcta_2_blazejkwiatkowski.R
import pl.dev.qcta_2_blazejkwiatkowski.apiData.FixerAPIDateConvertedData
import java.util.*
import kotlin.collections.ArrayList

class RatesRecyclerViewAdapter: RecyclerView.Adapter<RatesRecyclerViewAdapter.RatesViewHolder>(){

    lateinit var fixerAPIDateConvertedData: FixerAPIDateConvertedData
    var list: ArrayList<Float> = ArrayList()
    var currencyList: ArrayList<String> = ArrayList()

    inner class RatesViewHolder(view: View): RecyclerView.ViewHolder(view){
        val dateTextView: TextView = view.findViewById(R.id.dateTextView)
        val currencyTextView: TextView = view.findViewById(R.id.currencyTextView)
        val rateTextView: TextView = view.findViewById(R.id.rateTextView)
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
    }

    override fun getItemCount(): Int {
//        TODO
        return list.size
    }


    private fun setRowDetails(holder: RatesViewHolder, position: Int){


        if(position == list.size-168){
            holder.dateTextView.text = convertTimestampToStringDate(fixerAPIDateConvertedData.timestamp)
            Log.e("TAG", position.toString())
        }else{
            holder.dateTextView.text = ""
        }

        holder.currencyTextView.text = currencyList.elementAt(position)
        holder.rateTextView.text = list[position].toBigDecimal().toPlainString()


    }

    private fun convertTimestampToStringDate(timestamp: Int): String {
        val date = Date((timestamp).toLong()*1000)
        val sdf = SimpleDateFormat("dd-MM-yyyy")

        return sdf.format(date)
    }

}
