package com.example.wetherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil3.compose.AsyncImage
import com.example.wetherapp.api.NetworkResponse
import com.example.wetherapp.api.WetherModel
import com.google.android.gms.location.LocationServices
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Brush

@Composable
fun WetherPage(modifier: Modifier= Modifier,
               viewModel: WetherViewModel) {
    var city by remember {
        mutableStateOf("")
    }

    val wetherResult = viewModel.weatherResult.observeAsState()
    val keyboardcontroller = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val fusedLocationClient =
        remember {
            LocationServices.getFusedLocationProviderClient(context)
        }

    @SuppressLint("MissingPermission")
    fun fetchCurrentLocation() {

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->

                if (location != null) {

                    val latitude = location.latitude
                    val longitude = location.longitude

                    Log.d("LOCATION", "Lat: $latitude, Lon: $longitude")

                    viewModel.getData("$latitude,$longitude")

                } else {

                    Log.d("LOCATION", "Location is null")

                }

            }
            .addOnFailureListener {

                Log.d("LOCATION", "Failed to get location")

            }
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->

            if (isGranted) {
                fetchCurrentLocation()


            }

        }

    LaunchedEffect(Unit) {
        if (
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            fetchCurrentLocation()

        } else {

            permissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )

        }

    }
    val wetherCondition = when (val result = wetherResult.value) {
        is NetworkResponse.Success -> result.data.current.condition.text
        else -> "Clear"
    }

    WetherBackground(
        wetherCondition = wetherCondition
    ) {

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {

            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 12.dp),
                        shape = RoundedCornerShape(30.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.10f)
                        ),
                        border = BorderStroke(
                            1.dp,
                            Color.White.copy(alpha = 0.15f)
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 8.dp
                        )
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            IconButton(
                                onClick = {
                                    keyboardcontroller?.hide()
                                    viewModel.getData(city)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = Color.White
                                )
                            }

                            TextField(
                                value = city,
                                onValueChange = {
                                    city = it
                                },
                                modifier = Modifier.weight(1f),
                                placeholder = {
                                    Text(
                                        "Search city...",
                                        color = Color.LightGray
                                    )
                                },
                                singleLine = true,
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,

                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,

                                    cursorColor = Color.White,
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                )
                            )

                            IconButton(
                                onClick = {
                                    fetchCurrentLocation()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = "Current Location",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }

            }

            item {
                when (val result = wetherResult.value) {
                    is NetworkResponse.Error -> {
                        Text(text = result.message)
                    }

                    NetworkResponse.Loading -> {
                        CircularProgressIndicator()
                    }

                    is NetworkResponse.Success -> {
                        WetherDetails(data = result.data)
                    }

                    null -> {}
                }
            }
        }
    }
}

@Composable
fun WetherDetails(data: WetherModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Locaton Icon",
                modifier = Modifier.size(40.dp)

            )
            Text(text = data.location.name, fontSize = 30.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = data.location.country, fontSize = 18.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "${data.current.temp_c}°c",
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        AsyncImage(
            model = "https:${data.current.condition.icon}",
            contentDescription = data.current.condition.text,
            modifier = Modifier
                .size(160.dp)
                .padding(8.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = data.current.condition.text,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            HorizontalDivider(
                modifier = Modifier.weight(1f)
            )

            Text(
                text = " Weather Details ",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            HorizontalDivider(
                modifier = Modifier.weight(1f)
            )

        }

        Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier.padding(12.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    WetherCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.WaterDrop,
                        title = "Humidity",
                        value = data.current.humidity
                    )

                    WetherCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Air,
                        title = "Wind Speed",
                        value = "${data.current.wind_kph} km/h"
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    WetherCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.WaterDrop,
                        title = "Chance of Rain",
                        value = "${data.current.chance_of_rain}%"
                    )

                    WetherCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Thermostat,
                        title = "Feels Like",
                        value = "${data.current.feelslike_c}°C"
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    WetherCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Cloud,
                        title = "Pressure",
                        value = data.current.pressure_in
                    )

                    WetherCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Cloud,
                        title = "Cloud",
                        value = data.current.cloud
                    )
                }
            }
        }
    }






@Composable
fun WetherCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    value: String
) {

    Card(
        modifier = modifier
            .padding(8.dp)
            .height(130.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.12f)
        ),
        border = BorderStroke(
            1.dp,
            Color.White.copy(alpha = 0.20f)

        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(28.dp),
                tint = Color(0xFF64B5F6)
            )

            Column {

                Text(
                    text = title,
                    color = Color(0xFFB0BEC5),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = value,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

            }

        }

    }

}




