package morquecho.string.weathermood.data.mappers

import morquecho.string.weathermood.data.remote.WeatherCityDTO
import morquecho.string.weathermood.data.remote.WeatherDTO
import morquecho.string.weathermood.domain.Weather
import morquecho.string.weathermood.domain.WeatherCity

fun WeatherCityDTO.toDomain(): WeatherCity {
    return WeatherCity(
        id = this.id,
        name = this.name,
        weather = this.weather.map { it.toDomain() }
    )
}

fun WeatherDTO.toDomain(): Weather {
    return Weather(
        id = this.id,
        main = this.main,
        description = this.description,
        icon = this.icon
    )
}