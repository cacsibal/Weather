# CS 4518 - Mobile and Ubiquitous Computing - PA3
# Weather App - Charles Anton Sibal

## Overview
This is a modern, location-aware Android Weather Application built natively with Kotlin and Jetpack Compose. It fetches real-time weather data and a 5-day forecast using the OpenWeatherMap API based on the user's current GPS location.

## Features
* **Location-Aware Weather:** Automatically fetches weather data for your current location using Google Play Services `FusedLocationProviderClient`.
* **Current Conditions:** Displays real-time temperature, humidity, atmospheric pressure, precipitation, and sunrise/sunset times.
* **Forecast Data:** * 24-hour hourly forecast.
  * 5-day daily forecast with high/low temperatures.
* **Dynamic UI:** The app features a dynamic background gradient that changes based on the current weather conditions (e.g., clear, rain, snow) and the time of day.
* **Unit Selection:** Seamlessly toggle between Metric (°C, m/s), Imperial (°F, mph), and Standard (K, m/s) unit systems.
* **Pull-to-Refresh:** Swipe down to manually update the weather data.
* **Error Handling:** Graceful error handling with a bottom retry bar if the network or location fetch fails.

## Tech Stack & Architecture
* **UI Toolkit:** Jetpack Compose & Material Design 3
* **Architecture:** MVVM (Model-View-ViewModel) with Unidirectional Data Flow using `StateFlow`.
* **Networking:** Retrofit2 & Gson for REST API communication.
* **Location:** `play-services-location` for high-accuracy device coordinates.

## Setup & Installation

To run this project locally, you will need an API key from OpenWeatherMap.
