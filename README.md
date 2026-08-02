# 🌤️ Weather App

A modern Android Weather Application built using **Kotlin** and **Jetpack Compose** following the **MVVM (Model-View-ViewModel)** architecture. The app fetches real-time weather information from WeatherAPI and displays it in a clean and responsive Material 3 user interface.

---



## ✨ Features

- 🔍 Search weather by city name
- 🌡️ Real-time temperature
- 📍 Location and country
- 🌤️ Dynamic weather icons
- 🌡️ Feels Like temperature
- 💧 Humidity
- 💨 Wind Speed
- ☁️ Cloud Coverage
- 🌧️ Chance of Rain
- 📊 Atmospheric Pressure
- ⚡ Fast API integration
- 🎨 Clean Material 3 UI

---

## 🛠️ Built With

- Kotlin
- Jetpack Compose
- MVVM Architecture
- Retrofit
- Gson Converter
- Coroutines
- LiveData
- Coil
- Material 3
- WeatherAPI

---

## 📂 Project Structure

```
app
├── api
│   ├── WeatherApi.kt
│   ├── RetrofitInstance.kt
│   ├── NetworkResponse.kt
│   └── Models
│
├── ui
│   ├── WeatherPage.kt
│   └── Components
│
├── viewmodel
│   └── WeatherViewModel.kt
│
└── MainActivity.kt
```

---

## 🧠 Architecture

```
User

   │

   ▼

Jetpack Compose UI

   │

   ▼

WeatherViewModel

   │

   ▼

Retrofit

   │

   ▼

WeatherAPI

   │

   ▼

JSON Response

   │

   ▼

Compose UI Updates
```

---

## 🚀 Getting Started

### Clone the repository

```bash
git clone https://github.com/VinitDhillon/Weather-App.git
```

### Open in Android Studio

Open the project and allow Gradle to sync.

---

## 🔑 API Key

This project uses **WeatherAPI**.

1. Create a free account at:
   https://www.weatherapi.com/

2. Generate your API key.

3. Replace the placeholder with your API key:

```kotlin
const val apiKey = "YOUR_API_KEY"
```

---

## 📦 Dependencies

- Retrofit
- Gson Converter
- Coil
- Coil Network OkHttp
- Coroutines
- LiveData
- Material 3

---

## 🎯 Future Improvements

- 📍 Current Device Location
- ⭐ Favorite Cities
- 🗂️ Room Database
- 📅 7-Day Forecast
- ⏰ Hourly Forecast
- 🌙 Dark Mode
- 🔄 Pull to Refresh
- 🌐 Offline Cache

---

## 👨‍💻 Author

**Vinit Dhillon**

GitHub: https://github.com/VinitDhillon

---
