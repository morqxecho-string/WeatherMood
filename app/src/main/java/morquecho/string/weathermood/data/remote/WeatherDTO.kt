package morquecho.string.weathermood.data.remote

data class WeatherDTO(
    private val id: Int,
    private val main: String,
    private val description: String,
    private val icon: String
)