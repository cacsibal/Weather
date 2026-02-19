package wpics.weather.data

import retrofit2.http.GET
import retrofit2.http.Query
import wpics.weather.models.ForecastResponse
import wpics.weather.models.WeatherResponse

/**
 * Retrofit service interface for OpenWeatherMap API communication.
 *
 * @version 1.0
 */
interface WeatherAPI {

    /**
     * Retrieves the current weather conditions for a specific coordinate.
     *
     * @param lat Latitude of the location.
     * @param lon Longitude of the location.
     * @param key OpenWeather API key.
     * @param units The unit system (metric or imperial).
     *
     * @return A [WeatherResponse] object containing current atmospheric data.
     */
    @GET("weather")
    suspend fun getCurrent(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") key: String,
        @Query("units") units: String
    ): WeatherResponse

    /**
     * Retrieves the 5-day weather forecast for a specific coordinate.
     *
     * @param lat Latitude of the location.
     * @param lon Longitude of the location.
     * @param key OpenWeather API key.
     * @param units The unit system (metric or imperial).
     *
     * @return A [ForecastResponse] containing a list of 3-hour weather snapshots.
     */
    @GET("forecast")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") key: String,
        @Query("units") units: String
    ): ForecastResponse
}