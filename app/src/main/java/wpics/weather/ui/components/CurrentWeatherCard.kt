package wpics.weather.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import wpics.weather.models.WeatherResponse
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@Composable
fun CurrentWeatherCard(current: WeatherResponse, content: @Composable () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // City
        current.name?.let {
            Text(it, style = MaterialTheme.typography.titleLarge)
        }

        // Icon + Temp
        Row(verticalAlignment = Alignment.CenterVertically) {
            current.weather?.firstOrNull()?.icon?.let { icon ->
                AsyncImage(
                    model = "https://openweathermap.org/img/wn/$icon@2x.png",
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
            }
            current.main?.temp?.let { temp ->
                Text(
                    text = "${temp.roundToInt()}°",
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Description
        current.weather?.firstOrNull()?.description?.let {
            Text(it, style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(Modifier.height(8.dp))
        HorizontalDivider()
        Spacer(Modifier.height(8.dp))

        // Stats grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            current.main?.humidity?.let {
                StatItem("Humidity", "$it%")
            }
            current.main?.pressure?.let {
                StatItem("Pressure", "$it hPa")
            }
            val rain = current.rain?.lastHour ?: 0.0
            StatItem("Precip", "$rain mm")
        }

        Spacer(Modifier.height(8.dp))

        // Sunrise / Sunset
        val sunrise = current.sys?.sunrise?.let { formatUnixTime(it) }
        val sunset = current.sys?.sunset?.let { formatUnixTime(it) }
        if (sunrise != null || sunset != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                sunrise?.let { StatItem("Sunrise", it) }
                sunset?.let { StatItem("Sunset", it) }
            }
        }

        content()
    }
}

@Composable
private fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

private fun formatUnixTime(unix: Long): String {
    val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
    return sdf.format(Date(unix * 1000))
}