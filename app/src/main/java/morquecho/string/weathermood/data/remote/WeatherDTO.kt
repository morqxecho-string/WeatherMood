package morquecho.string.weathermood.data.remote

data class WeatherDTO(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)