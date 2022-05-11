package pl.dev.qcta_2_blazejkwiatkowski.apiData

import java.util.*
import kotlin.collections.ArrayList

data class FixerAPIDateConvertedData(
    val base: String,
    val date: String,
    val historical: Boolean,
    val currency: Set<String>,
    val rates: List<Float>,
    val success: Boolean,
    val timestamp: Int
)