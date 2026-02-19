package wpics.weather.models

/**
 * Represents the complete response from the OpenWeather "5-Day Forecast" API.
 *
 * @property list A list of weather snapshots taken every three hours for the next five days. Defaults to null if missing from JSON.
 *
 * @version 1.0
 */
data class ForecastResponse(
    val list: List<ForecastItem>? = null
)