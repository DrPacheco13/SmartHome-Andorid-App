package pt.ipp.estg.smarthome

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import pt.ipp.estg.smarthome.common.Navigation
import pt.ipp.estg.smarthome.Retrofit.SHRetrofitViewModel
import pt.ipp.estg.smarthome.ui.theme.AppTheme
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pt.ipp.estg.smarthome.Retrofit.Room.SHDevice
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(darkTheme = isDark.value) {
                Navigation()
                ExampleScreen()
                retrofitOnce = true
                RetrofitStart()
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ExampleScreen() : Boolean {
    var approved = true
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CALL_PHONE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE
        )
    )
    permissionsState.permissions.forEach { permits ->
        when(permits.permission)
        {
            Manifest.permission.CALL_PHONE -> {
                when {
                    permits.hasPermission ->
                    {
                        //Text(text= "Call Dial Permission Granted")
                    }
                    permits.shouldShowRationale ->
                    {
                        //Text(text= "Call Dial Permission for Support")
                    }
                    !permits.hasPermission && !permits.shouldShowRationale ->
                    {
                        //Text(text= "Call Dial Permission Denied. Go To App settings for enabling")
                        approved = false
                    }
                }
            }
            Manifest.permission.INTERNET -> {
                when {
                    permits.hasPermission ->
                    {
                        //Text(text= "INTERNET Permission Granted")
                    }
                    permits.shouldShowRationale ->
                    {
                        //Text(text= "INTERNET Permission for full app operation")
                    }
                    !permits.hasPermission && !permits.shouldShowRationale ->
                    {
                        //Text(text= "INTERNET Permission Denied. Go To App settings for enabling")
                        approved = false
                    }
                }
            }
            Manifest.permission.ACCESS_NETWORK_STATE -> {
                when {
                    permits.hasPermission ->
                    {
                        //Text(text= "ACCESS_NETWORK_STATE Permission Granted")
                    }
                    permits.shouldShowRationale ->
                    {
                        //Text(text= "ACCESS_NETWORK_STATE Permission for full app operation")
                    }
                    !permits.hasPermission && !permits.shouldShowRationale ->
                    {
                        //Text(text= "ACCESS_NETWORK_STATE Permission Denied. Go To App settings for enabling")
                        approved = false
                    }
                }
            }
            Manifest.permission.ACCESS_WIFI_STATE -> {
                when {
                    permits.hasPermission ->
                    {
                        //Text(text= "ACCESS_WIFI_STATE Permission Granted")
                    }
                    permits.shouldShowRationale ->
                    {
                        //Text(text= "ACCESS_WIFI_STATE Permission for full app operation")
                    }
                    !permits.hasPermission && !permits.shouldShowRationale ->
                    {
                        //Text(text= "ACCESS_WIFI_STATE Permission Denied. Go To App settings for enabling")
                        approved = false
                    }
                }
            }
            Manifest.permission.ACCESS_COARSE_LOCATION -> {
                when {
                    permits.hasPermission ->
                    {
                        //Text(text= "ACCESS_COARSE_LOCATION Permission Granted")
                    }
                    permits.shouldShowRationale ->
                    {
                        //Text(text= "ACCESS_COARSE_LOCATION Permission for full app operation")
                    }
                    !permits.hasPermission && !permits.shouldShowRationale ->
                    {
                        //Text(text= "ACCESS_COARSE_LOCATION Permission Denied. Go To App settings for enabling")
                        approved = false
                    }
                }
            }
            Manifest.permission.ACCESS_FINE_LOCATION -> {
                when {
                    permits.hasPermission ->
                    {
                        //Text(text= "ACCESS_FINE_LOCATION Permission Granted")
                    }
                    permits.shouldShowRationale ->
                    {
                        //Text(text= "ACCESS_FINE_LOCATION Permission for full app operation")
                    }
                    !permits.hasPermission && !permits.shouldShowRationale ->
                    {
                        //Text(text= "ACCESS_FINE_LOCATION Permission Denied. Go To App settings for enabling")
                        approved = false
                    }
                }
            }
        }
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if(event == Lifecycle.Event.ON_START) {
                    permissionsState.launchMultiplePermissionRequest()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )
    return approved
}

/**
 * Save theme boolean to a file
 * Add future features
 */

// should be saved in data store (use shared preference)
val isDark = mutableStateOf(false)

fun toggleLightTheme() {
    isDark.value = !isDark.value
}

/**
 * Calls Retrofit for a total of 25 devices, and stores every device that it gets back
 */
var retrofitOnce = false
@Composable
fun RetrofitStart() {
    val ip = "10.0.2.2"
    val shRetrofitViewModel: SHRetrofitViewModel = viewModel()
    val devices = shRetrofitViewModel.getAllDevices().observeAsState()//começa a 0 ou null
    if (retrofitOnce) {
        var work = true
        var port = 3000
        if (devices.value != null) {
            GetSharedDevicesFireStore()
            devices.value?.forEach {
                    shRetrofitViewModel.verifyDevices(it.Ip,it.Port)
            }
            do {
                val url = "http://$ip:$port"
                var alreadyExists = false

                devices.value?.forEach {
                    if (it.Ip == ip && it.Port == port.toString()) {
                        alreadyExists = true
                        shRetrofitViewModel.getStatus(url, it.Nome)
                    }
                }

                if (!alreadyExists) {
                    shRetrofitViewModel.getStatus(url, "")
                }

                if (port == 3015) {
                    work = false
                }

                port++
            } while (work)
            retrofitOnce = false
        }
    }
}

@Composable
fun GetSharedDevicesFireStore() {
    val db = Firebase.firestore
    val email = emailLoggedIn
    val shRetrofitViewModel: SHRetrofitViewModel = viewModel()
    db.collection("devices")
        .whereEqualTo("shared", email)
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                shRetrofitViewModel.insertDevice(
                    SHDevice(
                        document.data["port"].toString(),
                        document.data["ip"].toString(),
                        document.data["nome"].toString(),
                        document.data["tipo"].toString(),
                        document.data["mac"].toString(),
                )
                )

            }
        }
        .addOnFailureListener { exception ->
            Log.d("DOCUMENT RESULT", "Error getting documents: ", exception)
        }
}

/*
@Composable
fun teste() {
    val shRetrofitViewModel : SHRetrofitViewModel = viewModel()
    val data = shRetrofitViewModel.getAllDevices().observeAsState()//começa a 0 ou null

    if (data.value != null){
        Text(data.value.toString())
        println(data.value.toString())
    }else{
        Text("error")
    }
}


// For displaying preview in
// the Android Studio IDE emulator
@Preview(showBackground = true)
@Composable
fun DefaultPreviewMain() {
    AppTheme(darkTheme = true) {
        Scaffold()
    }
}
*/