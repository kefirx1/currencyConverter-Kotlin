package pl.dev.qcta_2_blazejkwiatkowski

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.dev.qcta_2_blazejkwiatkowski.databinding.ActivityExchangeBinding

class ExchangeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExchangeBinding
    private lateinit var currency: String
    private lateinit var value: String
    private lateinit var base: String
    private lateinit var date: String
    private lateinit var dateText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExchangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currency = intent.getStringExtra("currency")!!
        value = intent.getStringExtra("value")!!
        base = intent.getStringExtra("base")!!
        date = intent.getStringExtra("date")!!
        dateText = "Wartość z dnia: $date"

        setDetails()
    }

    private fun setDetails(){
        binding.baseTextView.text = base
        binding.currencySelectedTextView.text = currency
        binding.dateSelectedTextView.text = dateText
        binding.valueSelectedTextView.text = value

        binding.backButtonImageView.setOnClickListener{
            this.finish()
        }
    }

}