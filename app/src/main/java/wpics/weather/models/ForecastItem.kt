package wpics.weather.models

import com.google.gson.annotations.SerializedName

/**
 * A specific weather snapshot at a certain point in time within the forecast list.
 *
 * @property dtTxt The timestamp of the snapshot in text format (e.g., "2026-02-10 12:00:00"). Defaults to null if missing from JSON.
 * @property main Temperature and humidity statistics for this specific time. Defaults to null if missing from JSON.
 * @property weather Description and icon for this specific time. Defaults to null if missing from JSON.
 *
 * @version 1.0
 */
data class ForecastItem(
    @SerializedName("dt_txt") val dtTxt: String? = null,
    val main: MainData? = null,
    val weather: List<WeatherDesc>? = null
)