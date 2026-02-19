package wpics.weather.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import wpics.weather.ui.theme.*

/**
 * Provides a dynamic vertical gradient background based on the current weather icon.
 *
 * @param iconCode The OpenWeather icon string (e.g., "01d").
 * @param content The UI content to be drawn over the background.
 */
@Composable
fun WeatherBackground(iconCode: String, content: @Composable () -> Unit) {
    val baseBackground = MaterialTheme.colorScheme.background

    // Determine the atmospheric tint based on the icon suffix
    // TODO: Add your own background colors for the various different weather conditions
    val tint = when {
        else -> baseBackground
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(tint.copy(alpha = 0.15f), baseBackground)
    )

    Box(modifier = Modifier.fillMaxSize().background(gradient)) {
        content()
    }
}