package pl.dev.qcta_2_blazejkwiatkowski.viewModels

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import pl.dev.qcta_2_blazejkwiatkowski.apiData.FixerAPIDateData
import pl.dev.qcta_2_blazejkwiatkowski.apiData.Rates
import kotlin.reflect.full.memberProperties


class MainActivityViewModelTest{

    @Test
    fun correctlyConvertedData(){
        //given
        val viewModel = MainActivityViewModel()
        val ratesFake = Rates(0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f, 0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f)
        val fixerAPIDateDataFake = FixerAPIDateData("base", "date", historical = true, rates = ratesFake, success = true, timestamp = 0)

        //when
        val fixerAPIDateConvertedData = viewModel.convertToFixerAPIDateConvertedData(fixerAPIDateData = fixerAPIDateDataFake)

        //then
        assertThat(fixerAPIDateConvertedData.base).isEqualTo(fixerAPIDateDataFake.base)
        assertThat(fixerAPIDateConvertedData.historical).isEqualTo(fixerAPIDateDataFake.historical)
        assertThat(fixerAPIDateConvertedData.success).isEqualTo(fixerAPIDateDataFake.success)
        assertThat(fixerAPIDateConvertedData.timestamp).isEqualTo(fixerAPIDateDataFake.timestamp)
        assertThat(fixerAPIDateConvertedData.currency.size).isEqualTo(Rates::class.memberProperties.size)
        assertThat(fixerAPIDateConvertedData.dates.size).isEqualTo(Rates::class.memberProperties.size)
    }

}

