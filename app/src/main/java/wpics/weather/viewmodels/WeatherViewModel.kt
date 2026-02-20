package wpics.weather.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import wpics.weather.BuildConfig
import wpics.weather.data.WeatherAPI
import wpics.weather.models.UnitSystem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * ViewModel responsible for managing weather data fetching and state preservation.
 *
 * @property api The [WeatherAPI] instance used for network communication.
 *
 * @version 1.0
 */
class WeatherViewModel(private val api: WeatherAPI) : ViewModel() {

    /** Reads the API key from the BuildConfig. Your key should be added under local.properties */
    private val apiKey = BuildConfig.WEATHER_KEY

    /** Internal mutable state flow for the UI state. */
    private val _state = MutableStateFlow<WeatherUIState>(WeatherUIState.Loading)

    /** Public read-only state flow for the UI state. */
    val state: StateFlow<WeatherUIState> = _state.asStateFlow()

    /** Internal mutable state flow for the current unit system. */
    private val _unitSystem = MutableStateFlow(UnitSystem.METRIC)

    /** Public read-only state flow for the unit system. */
    val unitSystem: StateFlow<UnitSystem> = _unitSystem.asStateFlow()

    /** Internal mutable state flow for the swipe-to-refresh status. */
    private val _isRefreshing = MutableStateFlow(false)

    /** Public read-only state flow for the refreshing status. */
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    /** Internal mutable state flow for the last updated timestamp. */
    private val _lastUpdated = MutableStateFlow<String?>(null)

    /** Public read-only state flow for the last updated timestamp. */
    val lastUpdated: StateFlow<String?> = _lastUpdated

    /** Cache coordinates to allow re-fetching when units change **/
    private var lastLat: Double? = null
    private var lastLon: Double? = null

    /**
     * Fetches weather data from the OpenWeather API based on coordinates.
     *
     * @param lat Latitude of the device.
     * @param lon Longitude of the device.
     */
    fun fetchData(lat: Double, lon: Double) {
        lastLat = lat
        lastLon = lon

        viewModelScope.launch {
            try {
                val units = _unitSystem.value.requestValue

                val current = api.getCurrent(lat, lon, apiKey, units)
                val forecast = api.getForecast(lat, lon, apiKey, units)

                val hasForecast = !forecast.list.isNullOrEmpty()
                if(hasForecast) {
                    _lastUpdated.value = getFormattedTime()
                    _state.value = WeatherUIState.Success(
                        current = current,
                        forecast = forecast.list
                    )
                } else {
                    _state.value = WeatherUIState.Error("No forecast data available.")
                }
            } catch (e: Exception) {
                // 5. Explicit Error Handling
                // Logs the error to Logcat and updates the UI state to show the error message.
                e.printStackTrace()
                _state.value = WeatherUIState.Error(
                    msg = e.localizedMessage ?: "Check your internet connection or API key."
                )
            } finally {
                // 6. Reset Refreshing State
                _isRefreshing.value = false

                Log.d("WeatherViewModel.fetchData", _state.value.toString())
            }
        }
    }

    /**
     * Updates the global unit preference (Metric, Imperial, or Hybrid).
     *
     * @param system The new [UnitSystem] to apply.
     */
    fun updateUnitSystem(system: UnitSystem) {
        if(_unitSystem.value == system) return

        Log.d("WeatherViewModel.updateUnitSystem", "Unit system changed to $system")

        _unitSystem.value = system
        val lat = lastLat ?: return
        val lon = lastLon ?: return

        _isRefreshing.value = true
        fetchData(lat, lon)
    }

    /**
     * Sets the refreshing state for the Pull-to-Refresh UI component.
     *
     * @param value True if refreshing, false otherwise.
     */
    fun setRefreshing(value: Boolean) {
        _isRefreshing.value = value
    }

    /**
     * A helper function for getting a formatted timestamp.
     *
     * @return A string containing a date time
     */
    private fun getFormattedTime(): String {
        val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
        return sdf.format(Date())
    }
}