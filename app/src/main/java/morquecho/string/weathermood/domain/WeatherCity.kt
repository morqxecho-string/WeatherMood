package morquecho.string.weathermood.domain

import morquecho.string.weathermood.data.remote.ListWeatherDTO

data class WeatherCity(
    val id: Int,
    val name: String,
    val weather: ListWeatherDTO)