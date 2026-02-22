package wpics.weather.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import wpics.weather.ui.theme.*
import androidx.compose.ui.graphics.Color
import java.util.Calendar

/**
 * Provides a dynamic vertical gradient background based on the current weather icon.
 *
 * @param iconCode The OpenWeather icon string (e.g., "01d").
 * @param content The UI content to be drawn over the background.
 */
@Composable
fun WeatherBackground(iconCode: String, content: @Composable () -> Unit) {
    val tint = getTint(iconCode)
//    Log.d("WeatherBackground", "Tint: $tint")

    val gradient = Brush.verticalGradient(
        colors = listOf(tint.first.copy(alpha = 0.6f), tint.second)
    )

    Box(modifier = Modifier.fillMaxSize().background(gradient)) {
        content()
    }
}