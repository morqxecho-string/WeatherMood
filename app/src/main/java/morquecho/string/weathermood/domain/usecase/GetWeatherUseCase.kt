package morquecho.string.weathermood.domain.usecase

import morquecho.string.weathermood.data.repository.WeatherRepository
import morquecho.string.weathermood.domain.WeatherCity
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(city: String): WeatherCity {
        return repository.getWeather(city)
    }
}