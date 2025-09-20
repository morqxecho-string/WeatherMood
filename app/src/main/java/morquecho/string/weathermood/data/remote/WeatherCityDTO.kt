package morquecho.string.weathermood.data.remote

data class WeatherCityDTO(
    val id: Int,
    val name: String,
    val weather: List<WeatherDTO>)