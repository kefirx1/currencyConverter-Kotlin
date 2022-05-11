package pl.dev.qcta_2_blazejkwiatkowski

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.dev.qcta_2_blazejkwiatkowski.databinding.ActivityExchangeBinding

class ExchangeActivity : AppCompatActivity() {

    lateinit var binding: ActivityExchangeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExchangeBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}