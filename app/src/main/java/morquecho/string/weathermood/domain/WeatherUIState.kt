package morquecho.string.weathermood.domain

sealed class WeatherUIState {
    object Loading : WeatherUIState()
    data class Success(val data: WeatherCity) : WeatherUIState()
    data class Error(val message: String) : WeatherUIState()
    object Initial : WeatherUIState()
}