package wpics.weather.ui.theme

import androidx.compose.ui.graphics.Color
import java.util.Calendar

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val morningOrange = Color(0xFFFFB347)
val morningBlue = Color(0xFF87CEEB)
val afternoonBlue = Color(0xFF4A90D9)
val eveningPurple = Color(0xFF2C1654)
val eveningRed = Color(0xFFFF6B6B)
val nightNavy = Color(0xFF1C1C5E)
val black = Color(0xFF000000)

fun getTint(iconCode: String = ""): Pair<Color, Color> {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val isNight = iconCode.endsWith("n")

    // add tint based on weather icon
    val weatherTint: Pair<Color, Color>? = when {
        iconCode.startsWith("01") -> null // clear -> no change
        iconCode.startsWith("02") || iconCode.startsWith("03") -> Pair(Color(0xFF9DB8C9), Color(0xFF6E8FA0)) // few clouds
        iconCode.startsWith("04") -> Pair(Color(0xFF6E7E85), Color(0xFF4A555A)) // overcast
        iconCode.startsWith("09") || iconCode.startsWith("10") -> Pair(Color(0xFF4A6FA5), Color(0xFF2C3E6B)) // rain
        iconCode.startsWith("11") -> Pair(Color(0xFF3D3D5C), Color(0xFF1A1A2E)) // thunderstorm
        iconCode.startsWith("13") -> Pair(Color(0xFFB0C4DE), Color(0xFF8FA8BF)) // snow
        iconCode.startsWith("50") -> Pair(Color(0xFF8E9EAB), Color(0xFF6B7A83)) // fog
        else -> null
    }

    return weatherTint ?: when {
        isNight -> Pair(nightNavy, black)
        hour in 6..11 -> Pair(morningOrange, morningBlue)
        hour in 12..17 -> Pair(morningBlue, afternoonBlue)
        hour in 18..20 -> Pair(eveningRed, eveningPurple)
        else -> Pair(nightNavy, black)
    }
}

fun getTextColor(): Color = when(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
    in 6..20 -> Color.Unspecified
    else     -> Color.White // white for night time to see text better
}