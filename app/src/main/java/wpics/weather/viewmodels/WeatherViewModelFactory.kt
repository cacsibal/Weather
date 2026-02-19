package wpics.weather.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import wpics.weather.data.WeatherAPI

/**
 * Factory for creating [WeatherViewModel] instances with the required API dependency.
 *
 * @property api The network service required by the ViewModel.
 *
 * @version 1.0
 */
class WeatherViewModelFactory(private val api: WeatherAPI) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the requested ViewModel class.
     *
     * @param modelClass The class of the ViewModel to create.
     * @return A newly created ViewModel instance.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(api) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}