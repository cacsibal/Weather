package wpics.weather.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import wpics.weather.ui.components.*
import wpics.weather.viewmodels.WeatherUIState
import wpics.weather.viewmodels.WeatherViewModel

/**
 * Primary Composable for the weather UI.
 *
 * @param viewModel A [WeatherViewModel] instance
 * @param onRefresh A callback function for handling refresh
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(viewModel: WeatherViewModel, onRefresh: () -> Unit) {
    val uiState by viewModel.state.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val lastUpdated by viewModel.lastUpdated.collectAsState()
    val currentUnits by viewModel.unitSystem.collectAsState()

    val icon = (uiState as? WeatherUIState.Success)?.current?.weather?.firstOrNull()?.icon ?: ""

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(0.dp, 15.dp), // Ensures the UI starts below the webcam/notch
        topBar = {
            UnitSelector(currentUnits) { selectedUnit ->
                // This call must trigger the ViewModel's updateUnitSystem,
                // which we updated to re-run fetchData()
                viewModel.updateUnitSystem(selectedUnit)
            }
        },
        bottomBar = {
            // Only show the bottom bar if we are in an Error state
            if (uiState is WeatherUIState.Error) {
                BottomRetryBar(lastUpdated, onRefresh)
            }
        }
    ) { innerPadding ->
        WeatherBackground(iconCode = icon) {
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                when (val state = uiState) {
                    is WeatherUIState.Loading -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    is WeatherUIState.Error -> {
                        Box(Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
                            Text(state.msg, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.error)
                        }
                    }
                    is WeatherUIState.Success -> {
                        Log.d("WeatherScreen", "WeatherState.Success")

                        val current = state.current
                        val forecast = state.forecast

//                        Log.d("WeatherScreen", "Current: $current")
//                        Log.d("WeatherScreen", "Forecast: $forecast")
                    }
                }
            }
        }
    }
}