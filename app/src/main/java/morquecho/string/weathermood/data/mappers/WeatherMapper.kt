package morquecho.string.weathermood.data.mappers

import morquecho.string.weathermood.data.remote.WeatherCityDTO
import morquecho.string.weathermood.domain.WeatherCity

fun WeatherCityDTO.toDomain(): WeatherCity {
    return WeatherCity(
        id = this.id,
        name = this.name,
        weather = this.weather
    )
}