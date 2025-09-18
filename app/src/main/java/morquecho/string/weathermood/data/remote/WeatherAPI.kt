package morquecho.string.weathermood.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("data/2.5/weather")
    suspend fun getWeather(@Query("q") city: String,
                           @Query("appid") apiKey: String): WeatherCityDTO
}