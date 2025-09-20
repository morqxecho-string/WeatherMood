package morquecho.string.weathermood.domain

data class WeatherCity(
    val id: Int,
    val name: String,
    val weather: List<Weather>)