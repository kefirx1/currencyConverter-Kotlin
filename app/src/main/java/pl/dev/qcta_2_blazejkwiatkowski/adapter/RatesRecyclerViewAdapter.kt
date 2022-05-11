package pl.dev.qcta_2_blazejkwiatkowski.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.dev.qcta_2_blazejkwiatkowski.R
import pl.dev.qcta_2_blazejkwiatkowski.apiData.FixerAPIDateConvertedData

class RatesRecyclerViewAdapter: RecyclerView.Adapter<RatesRecyclerViewAdapter.RatesViewHolder>(){
    var fixerAPIDateConvertedData: FixerAPIDateConvertedData? = null
    var currencyNamesSet: Set<String>? = null

    inner class RatesViewHolder(view: View): RecyclerView.ViewHolder(view){

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
        return currencyNamesSet!!.size
    }

    private fun setRowDetails(holder: RatesViewHolder, position: Int){

        if(position == 167){
            Log.e("TAG","CALL previous day")
        }

        if(currencyNamesSet != null && fixerAPIDateConvertedData != null){
            holder.currencyTextView.text = currencyNamesSet!!.elementAt(position)
            holder.rateTextView.text = fixerAPIDateConvertedData!!.rates[currencyNamesSet!!.elementAt(position)]!!.toBigDecimal().toPlainString()
        }else{
            Log.e("TAG", "NULL")
        }

    }

}
