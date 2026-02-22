package wpics.weather.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import wpics.weather.models.ForecastItem
import wpics.weather.ui.theme.getTint
import kotlin.math.roundToInt

@Composable
fun ForecastInformation(forecast: List<ForecastItem>, content: @Composable () -> Unit = {}) {
    val next24h = forecast
        .filter { item ->
            val dtTxt = item.dtTxt ?: return@filter false
            val sdf = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault())
            val itemTime = sdf.parse(dtTxt)?.time ?: return@filter false
            itemTime >= System.currentTimeMillis()
        }
        .take(8)

    val dailyGroups = forecast
        .filter { it.dtTxt != null && it.main?.temp != null }
        .groupBy { it.dtTxt!!.substring(0, 10) }
        .entries
        .drop(1) // skip today
        .take(5)

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Next 24 Hours",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(next24h) { item ->
                val time = item.dtTxt?.let { // convert to match local time zone
                    val sdf = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).apply {
                        timeZone = java.util.TimeZone.getTimeZone("UTC")
                    }
                    val date = sdf.parse(it) ?: return@let ""
                    java.text.SimpleDateFormat("h:mm a", java.util.Locale.getDefault()).format(date)
                } ?: ""
                val temp = item.main?.temp?.roundToInt()
                val icon = item.weather?.firstOrNull()?.icon
                val rain = item.rain?.lastHour

                HourlyForecastCard(time, temp, icon, rain)
            }
        }

        // 5-day forecast
        Text(
            text = "5-Day Forecast",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            dailyGroups.forEach { (dateStr, items) ->
                val temps = items.mapNotNull { it.main?.temp }
                val low = temps.min().roundToInt()
                val high = temps.max().roundToInt()
                val icon = (items.firstOrNull { it.dtTxt!!.contains("12:00") } ?: items.first())
                    .weather?.firstOrNull()?.icon
                val day = getDayLabel(dateStr)

                DayForecastCard(day, low, high, icon)
            }
        }

        if (dailyGroups.size < 5) {
            Text(
                text = "Only ${dailyGroups.size} days of forecast available",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        content()
    }
}

@Composable
private fun HourlyForecastCard(time: String, temp: Int?, icon: String?, rain: Double?) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = getTint().first.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                time,
                style = MaterialTheme.typography.labelMedium
            )

            icon?.let {
                AsyncImage(
                    model = "https://openweathermap.org/img/wn/$it@2x.png",
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
            }

            temp?.let {
                Text("$it°",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = if (rain != null) "$rain mm" else "—",
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}

@Composable
private fun DayForecastCard(day: String, low: Int, high: Int, icon: String?) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = getTint().first.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                day,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold)

            icon?.let {
                AsyncImage(
                    model = "https://openweathermap.org/img/wn/$it@2x.png",
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }

            Text("$high°",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold)
            Text(
                "$low°",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

private fun getDayLabel(dateStr: String): String {
    return try {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        val cal = java.util.Calendar.getInstance().apply { time = sdf.parse(dateStr)!! }
        java.text.SimpleDateFormat("EEE", java.util.Locale.getDefault()).format(cal.time)
    } catch (e: Exception) {
        e.printStackTrace()
    } as String
}