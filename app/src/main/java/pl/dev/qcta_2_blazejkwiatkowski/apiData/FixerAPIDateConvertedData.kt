package pl.dev.qcta_2_blazejkwiatkowski.apiData

data class FixerAPIDateConvertedData(
    val base: String,
    val date: String,
    val historical: Boolean,
    val currency: Set<String>,
    val rates: List<Float>,
    val success: Boolean,
    val timestamp: Int
)