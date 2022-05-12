package pl.dev.qcta_2_blazejkwiatkowski

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import pl.dev.qcta_2_blazejkwiatkowski.databinding.ActivityExchangeBinding

class ExchangeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExchangeBinding
    var currency = ""
    var value = ""
    var base = ""
    var date = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExchangeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        currency = intent.getStringExtra("currency")!!
        value = intent.getStringExtra("value")!!
        base = intent.getStringExtra("base")!!
        date = intent.getStringExtra("date")!!

        val dateText = "Wartość z dnia: $date"

        binding.baseTextView.text = base
        binding.currencySelectedTextView.text = currency
        binding.dateSelectedTextView.text = dateText
        binding.valueSelectedTextView.text = value

        binding.backButtonImageView.setOnClickListener{
            this.finish()
        }


    }
}