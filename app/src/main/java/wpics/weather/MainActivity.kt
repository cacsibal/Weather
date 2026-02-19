package wpics.weather

import android.Manifest
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.google.android.gms.location.LocationServices
import wpics.weather.ui.WeatherScreen
import wpics.weather.ui.theme.WeatherTheme
import wpics.weather.viewmodels.WeatherViewModel
import wpics.weather.viewmodels.WeatherViewModelFactory

/**
 * The entry point activity that handles permissions and location triggers.
 *
 * @version 1.0
 */
class MainActivity : ComponentActivity() {

    /** Initialization of the ViewModel using the Manual DI container. */
    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory((application as WeatherApp).container.api)
    }

    /** Launcher to handle location permission requests. */
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
            getLocationAndFetch()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        requestPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))

        setContent {
            WeatherTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    WeatherScreen(
                        viewModel = viewModel,
                        onRefresh = {
                            viewModel.setRefreshing(true)
                            getLocationAndFetch()
                        }
                    )
                }
            }
        }
    }

    /**
     * Utilizes FusedLocationProvider to get coordinates and trigger the ViewModel.
     */
    private fun getLocationAndFetch() {
        val fusedClient = LocationServices.getFusedLocationProviderClient(this)

        // Define a Priority for high accuracy
        val priority = com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY

        try {
            // CancellationToken allows the request to be canceled if it takes too long
            val cts = com.google.android.gms.tasks.CancellationTokenSource()

            fusedClient.getCurrentLocation(priority, cts.token).addOnSuccessListener { location: Location? ->
                if (location != null) {
                    Log.d("WeatherApp", "Fetched Location: ${location.latitude}, ${location.longitude}")
                    viewModel.fetchData(
                        lat = location.latitude,
                        lon = location.longitude
                    )
                } else {
                    // Handle case where GPS is off
                    Log.e("WeatherApp", "Location is null. GPS may be disabled.")
                    viewModel.setRefreshing(false)
                }
            }.addOnFailureListener { e ->
                Log.e("WeatherApp", "Location fetch failed: ${e.message}")
                viewModel.setRefreshing(false)
            }
        } catch (e: SecurityException) {
            Log.e("WeatherApp", "Permission error: ${e.message}")
            viewModel.setRefreshing(false)
        }
    }
}