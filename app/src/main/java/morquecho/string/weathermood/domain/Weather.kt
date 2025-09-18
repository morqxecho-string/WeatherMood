package morquecho.string.weathermood.domain

data class Weather(
    private val id: Int,
    private val main: String,
    private val description: String,
    private val icon: String
)