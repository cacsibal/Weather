package wpics.weather.viewmodels

import wpics.weather.models.ForecastItem
import wpics.weather.models.WeatherResponse

/**
 * Sealed interface representing the state of the Weather screen.
 *
 * @version 1.0
 */
sealed interface WeatherUIState {

    /**
     * Indicates that a network request is currently in progress.
     */
    object Loading : WeatherUIState

    /**
     * Represents a successful data fetch with current and forecast data.
     *
     * @property current The [WeatherResponse] containing current conditions.
     * @property forecast The list of [ForecastItem] for the 5-day outlook.
     */
    data class Success(
        val current: WeatherResponse,
        val forecast: List<ForecastItem>
    ) : WeatherUIState

    /**
     * Represents a failure in fetching or processing weather data.
     *
     * @property msg The error message to be displayed to the user.
     */
    data class Error(val msg: String) : WeatherUIState
}