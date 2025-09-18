package morquecho.string.weathermood.data.repository

import morquecho.string.weathermood.data.mappers.toDomain
import morquecho.string.weathermood.data.remote.WeatherAPI
import morquecho.string.weathermood.domain.WeatherCity

class WeatherRepositoryImpl (
    private val weatherApi: WeatherAPI,
    private val apiKey: String
) : WeatherRepository {
    override suspend fun getWeather(city: String): WeatherCity {
        return weatherApi.getWeather(city, apiKey).toDomain()
    }
}