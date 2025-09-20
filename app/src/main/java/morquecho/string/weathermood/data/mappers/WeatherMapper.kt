package morquecho.string.weathermood.data.mappers

import morquecho.string.weathermood.data.remote.WeatherCityDTO
import morquecho.string.weathermood.data.remote.WeatherDTO
import morquecho.string.weathermood.domain.Weather
import morquecho.string.weathermood.domain.WeatherCity

fun WeatherCityDTO.toDomain(): WeatherCity {
    return WeatherCity(
        id = this.id,
        name = this.name,
        weather = this.weather.map { it.toDomain() },
        temp = this.main.temp,
        feelsLike = this.main.feelsLike,
        tempMin = this.main.tempMin,
        tempMax = this.main.tempMax,
        humidity = this.main.humidity,
        windSpeed = this.wind.speed,
        windDeg = this.wind.deg,
        sunrise = this.sys.sunrise,
        sunset = this.sys.sunset,
        country = this.sys.country,
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