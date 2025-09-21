package morquecho.string.weathermood.data.repository

import morquecho.string.weathermood.data.mappers.toDomain
import morquecho.string.weathermood.data.remote.WeatherAPI
import morquecho.string.weathermood.di.ApiKey
import morquecho.string.weathermood.domain.WeatherCity
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherAPI,
    @ApiKey private val apiKey: String
) : WeatherRepository {
    override suspend fun getWeather(city: String, unit: String): Result<WeatherCity> {
        // return weatherApi.getWeather(city, apiKey, unit).toDomain()
        return try {
            val weatherDto = weatherApi.getWeather(city, apiKey, unit)
            Result.success(weatherDto.toDomain())
        } catch (e: Exception) {
            Result.failure(Exception("Error en la respuesta de la API: ${e.toString()}"))
        }
    }
}