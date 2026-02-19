package wpics.weather.models

/**
 * Defines the available unit systems for displaying weather data.
 *
 * @property displayName The text shown to the user in the UI.
 * @property requestValue The string sent to the OpenWeather API (Metric vs. Imperial).
 *
 * @version 1.0
 */
enum class UnitSystem(val displayName: String, val requestValue: String) {
    /** Celsius for temperature and meters/second for wind. */
    METRIC("Metric (°C, m/s)", "metric"),

    /** Fahrenheit for temperature and miles/hour for wind. */
    IMPERIAL("Imperial (°F, mph)", "imperial"),

    /** Standard temperature provided by the api. */
    STANDARD("Standard (K, m/s)", "standard")
}