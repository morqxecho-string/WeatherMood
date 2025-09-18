package morquecho.string.weathermood.data.repository

import morquecho.string.weathermood.domain.WeatherCity

interface WeatherRepository {
    suspend fun getWeather(city: String): WeatherCity
}