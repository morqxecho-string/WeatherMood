package morquecho.string.weathermood.presentation.ui.screens

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import morquecho.string.weathermood.data.local.LastCityPreferences
import morquecho.string.weathermood.data.local.LastUnitPreferences
import morquecho.string.weathermood.domain.WeatherCity
import morquecho.string.weathermood.domain.WeatherUIState
import morquecho.string.weathermood.domain.usecase.GetWeatherUseCase
import javax.inject.Inject

@HiltViewModel
open class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val lastCityPreferences: LastCityPreferences,
    private val lastUnitPreferences: LastUnitPreferences
): ViewModel() {
    private val _stateUnit: MutableStateFlow<String> = MutableStateFlow("imperial")
    private val _stateCity: MutableStateFlow<String> = MutableStateFlow("")
    private val _uiWeatherState: MutableStateFlow<WeatherUIState> = MutableStateFlow(WeatherUIState.Initial)

    val stateUnit: StateFlow<String> = _stateUnit.asStateFlow()
    val stateCity: StateFlow<String> = _stateCity.asStateFlow()
    val uiWeatherState: StateFlow<WeatherUIState> = _uiWeatherState.asStateFlow()

    init {
        viewModelScope.launch {
            val lastUnit: String? = lastUnitPreferences.getLastUnit()
            if (lastUnit != null)
                _stateUnit.value = lastUnit

            val lastCity: WeatherCity? = lastCityPreferences.getLastWeatherCityObject()
            if (lastCity == null) return@launch;

            _uiWeatherState.value = WeatherUIState.Success(lastCity)
            _stateCity.value = lastCity.name
        }
    }

    fun loadWeatherCity(city: String) {
        viewModelScope.launch {
            _uiWeatherState.value = WeatherUIState.Loading

            val resultWeatherCity : Result<WeatherCity> = getWeatherUseCase(city, stateUnit.value)

            resultWeatherCity.onSuccess { weatherCity ->
                if (weatherCity.country != "US") {
                    _uiWeatherState.value = WeatherUIState.Error("You must search an US city only :)")
                    return@onSuccess
                }

                lastCityPreferences.saveLastWeatherCityObject(weatherCity)

                _stateCity.value = city
                _uiWeatherState.value = WeatherUIState.Success(weatherCity)

            }.onFailure {
                _uiWeatherState.value = WeatherUIState.Error("Error: ${it.toString()}")
            }
        }
    }

    /**
     * Function to set a specific unit temperature.
     * Validates if theres is a city already showing to execute again the API.
     * @param unit Unit temperature to set
     *
     * "Fahrenheit" -> "imperial"
     *
     * "Celsius" -> "metric"
     *
     * "Kelvin" -> "standard"
     *
     * default -> "standard"
     */
    fun setUnit(unit: String) {
        viewModelScope.launch {
            _stateUnit.value = unit
            _uiWeatherState.value = WeatherUIState.Loading

            // If theres is not a city already showing then avoid execute API
            if (_stateCity.value.isEmpty()) return@launch

            val resultWeatherCity : Result<WeatherCity> = getWeatherUseCase(stateCity.value, stateUnit.value)
            resultWeatherCity.onSuccess { weatherCity ->
                lastCityPreferences.saveLastWeatherCityObject(weatherCity)
                _uiWeatherState.value = WeatherUIState.Success(weatherCity)

            }.onFailure {
                _uiWeatherState.value = WeatherUIState.Error("Error: ${it.toString()}")
            }
        }
    }
}