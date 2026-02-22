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

fun getTint(): Pair<Color, Color> = when(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
    in 6..11 -> Pair(morningOrange, morningBlue)
    in 12..17 -> Pair(morningBlue, afternoonBlue)
    in 18..20 -> Pair(eveningRed, eveningPurple)
    else -> Pair(nightNavy, black)
}

fun getTextColor(): Color = when(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
    in 6..20 -> Color.Unspecified
    else     -> Color.White // white for night time
}