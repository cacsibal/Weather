package wpics.weather

import android.app.Application
import wpics.weather.data.AppContainer

/**
 * Application class responsible for maintaining the [AppContainer] lifecycle.
 *
 * @version 1.0
 */
class WeatherApp : Application() {

    /** The dependency injection container. @version 1.0 */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}