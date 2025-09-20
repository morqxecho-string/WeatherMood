package morquecho.string.weathermood.presentation.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import morquecho.string.weathermood.domain.WeatherCity
import morquecho.string.weathermood.domain.usecase.GetWeatherUseCase
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val getWeatherUseCase: GetWeatherUseCase): ViewModel() {
    private val _state = MutableStateFlow<WeatherCity?>(null)
    val state = _state.asStateFlow()

    fun loadWeatherCity(city: String) {
        viewModelScope.launch {
            val weather = getWeatherUseCase(city)
            _state.value = weather
        }
    }
}