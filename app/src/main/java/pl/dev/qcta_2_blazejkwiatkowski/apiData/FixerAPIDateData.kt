package pl.dev.qcta_2_blazejkwiatkowski.apiData

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FixerAPIDateData(
    val base: String,
    val date: String,
    val historical: Boolean,
    val rates: Rates,
    val success: Boolean,
    val timestamp: Int
)