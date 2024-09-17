package com.aj.proiecteim.tema8

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.reply.ui.theme.MaterialThemeEIM
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class Tema8 : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        this.actionBar?.hide()

        super.onCreate(savedInstanceState)

        Configuration.getInstance().userAgentValue = applicationContext.packageName
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            MaterialThemeEIM {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MapScreen(fusedLocationClient)
                }
            }
        }

        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }*/
    }
}

@Composable
fun MapScreen(fusedLocationClient: FusedLocationProviderClient) {
    var mapView by remember { mutableStateOf<MapView?>(null) }
    var targetLocation by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val currentLocation = GeoPoint(location.latitude, location.longitude)
                    mapView?.controller?.setCenter(currentLocation)

                    mapView?.overlays?.clear()
                    val marker = Marker(mapView)
                    marker.position = currentLocation
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    mapView?.overlays?.add(marker)
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AndroidView(
                factory = {
                    MapView(context).apply {
                        mapView = this
                        setMultiTouchControls(true)
                        controller.setZoom(15.0)
                    }
                },
                modifier = Modifier.weight(1f).fillMaxWidth()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .align(Alignment.TopStart)
                .imePadding(),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = targetLocation,
                onValueChange = { targetLocation = it },
                label = { Text("Introduceți o locație (lat,lng)") },
                shape = RoundedCornerShape(100.dp),
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(MaterialTheme.colorScheme.secondary)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val latLng = targetLocation.text.split(",").map { it.trim() }
                    if (latLng.size == 2) {
                        val lat = latLng[0].toDoubleOrNull()
                        val lng = latLng[1].toDoubleOrNull()
                        if (lat != null && lng != null) {
                            val point = GeoPoint(lat, lng)
                            mapView?.controller?.setCenter(point)

                            mapView?.overlays?.clear()
                            val marker = Marker(mapView)
                            marker.position = point
                            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            mapView?.overlays?.add(marker)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Duceți-vă la locație")
            }
        }
    }
}
