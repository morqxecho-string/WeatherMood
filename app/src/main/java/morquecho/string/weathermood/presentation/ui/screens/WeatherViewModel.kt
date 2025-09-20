package morquecho.string.weathermood.presentation.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import morquecho.string.weathermood.data.local.LastCityPreferences
import morquecho.string.weathermood.domain.WeatherCity
import morquecho.string.weathermood.domain.usecase.GetWeatherUseCase
import javax.inject.Inject

@HiltViewModel
open class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val lastCityPreferences: LastCityPreferences
): ViewModel() {
    private val _state = MutableStateFlow<WeatherCity?>(null)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val lastCity: WeatherCity? = lastCityPreferences.getLastWeatherCityObject()
            if (lastCity == null) return@launch;

            _state.value = lastCity
        }
    }

    fun loadWeatherCity(city: String) {
        viewModelScope.launch {
            val weather = getWeatherUseCase(city)
            lastCityPreferences.saveLastWeatherCityObject(weather)
            _state.value = weather
        }
    }
}