package pt.ipp.estg.smarthome

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import pt.ipp.estg.smarthome.common.LocationDetails

var det = LocationDetails(34.064460, 241.616143)

/**
 * Calls the google map with a marker on current location
 */
@Composable
fun currLocMap() {
    val context = LocalContext.current
    //val coords = getLocationWithCheckNetworkAndGPS(context)
    val coords = LocationUpdates()
    val currentLocation: LatLng?
    currentLocation = LatLng(coords.latitude, coords.longitude)
    //Log.d(" Maps Debug: ", "${det.latitude} and ${det.longitude}")
    Log.d(" Maps Debug 3rd stage: ", "${currentLocation.latitude} and ${currentLocation.longitude}")
    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPosition
    ) {
        Marker(
            position = currentLocation,
            title = "Current Location",
            snippet = "Marker in current location: lat = ${currentLocation.latitude}, lgt = ${currentLocation.longitude}"
        )
    }
}

/**
 * Get current coordinates, verifies if the user granted permissions
 */
fun getLocationWithCheckNetworkAndGPS(mContext: Context): Location? {
    val lm = (mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
    val isGpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    val isNetworkLocationEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    var networkLocation: Location? = null
    var gpsLocation: Location? = null
    var finalLoc: Location? = null

    if (isGpsEnabled) {
        gpsLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
    }
    if (isNetworkLocationEnabled) {
        networkLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
    }
    if (gpsLocation != null && networkLocation != null) {
        //smaller the number more accurate result will
        finalLoc = if (gpsLocation.accuracy > networkLocation.accuracy) {
            gpsLocation
        } else {
            networkLocation
        }
    } else {
        if (gpsLocation != null) {
            finalLoc = gpsLocation
        } else if (networkLocation != null) {
            finalLoc = networkLocation
        }
    }
    Log.d(" Maps Debug 3rd stage: ", "${finalLoc?.latitude} and ${finalLoc?.longitude}")
    return finalLoc
}

@Composable
fun LocationUpdates() : LocationDetails{
    val ctx = LocalContext.current
    val locationStatus = remember { mutableStateOf("") }
    val lat = 34.064460
    val lng = 241.616143
    val currLoc = LocationDetails(lat,lng)

    DisposableEffect(Unit){
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(ctx)
        fusedLocationClient.lastLocation.addOnSuccessListener {
            locationStatus.value = "Location lat: ${it.latitude} , lon: ${it.longitude}"
            currLoc.latitude=it.latitude
            currLoc.longitude=it.longitude
        }.addOnFailureListener{
            locationStatus.value = "Unable to get locations"
        }
        Log.d(" Maps Debug 1st stage: ", "${currLoc.latitude} and ${currLoc.longitude} and ${locationStatus.value}")

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            10000,
        ).setMinUpdateIntervalMillis(30000).build();

        val locationCallback = object  : LocationCallback() {
            override fun onLocationResult(locations: LocationResult) {
                for(location in locations.locations){
                    locationStatus.value = "Location lat: ${location.latitude} , lon: ${location.longitude}"
                    currLoc.latitude=location.latitude
                    currLoc.longitude=location.longitude
                    Log.d(" Maps Debug 2nd stage: ", "${location.latitude} and ${location.longitude} and ${locationStatus.value}")
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback,null)
        onDispose {  fusedLocationClient.removeLocationUpdates(locationCallback)  }
    }
    return currLoc
}
