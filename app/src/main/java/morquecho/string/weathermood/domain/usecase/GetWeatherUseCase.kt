package morquecho.string.weathermood.domain.usecase

import morquecho.string.weathermood.data.repository.WeatherRepository
import morquecho.string.weathermood.domain.WeatherCity
import javax.inject.Inject

open class GetWeatherUseCase @Inject constructor(private val repository: WeatherRepository) {
    open suspend operator fun invoke(city: String, unit: String): Result<WeatherCity> {
        return repository.getWeather(city, unit)
    }
}