package morquecho.string.weathermood.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import morquecho.string.weathermood.domain.WeatherCity
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class LastCityPreferences @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("last_city_prefs", Context.MODE_PRIVATE)

    private val gson = Gson()

    fun saveLastWeatherCityObject(weatherCity: WeatherCity) {
        val jsonString = gson.toJson(weatherCity)
        sharedPreferences.edit { putString("last_weather_city_object", jsonString) }
    }

    fun getLastWeatherCityObject(): WeatherCity? {
        val jsonString = sharedPreferences.getString("last_weather_city_object", null)
        return gson.fromJson(jsonString, WeatherCity::class.java)
    }
}