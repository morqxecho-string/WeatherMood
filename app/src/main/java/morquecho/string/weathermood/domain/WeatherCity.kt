package morquecho.string.weathermood.domain

data class WeatherCity(
    val id: Int,
    val name: String,
    val weather: List<Weather>,
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val humidity: Int,
    val windSpeed: Double,
    val windDeg: Int,
    val sunrise: Long,
    val sunset: Long,
    val country: String
)

// Clases de dominio anidadas para la UI.
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
