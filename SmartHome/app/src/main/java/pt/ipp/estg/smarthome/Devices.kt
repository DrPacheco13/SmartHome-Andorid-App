package pt.ipp.estg.smarthome


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import pt.ipp.estg.smarthome.Retrofit.Room.SHDevice
import pt.ipp.estg.smarthome.Retrofit.SHRetrofitViewModel
import pt.ipp.estg.smarthome.common.DeviceNotification
import pt.ipp.estg.smarthome.ui.theme.AppTheme
import pt.ipp.estg.smarthome.ui.theme.quickSand

class Devices : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

/**
 * Verify if the device is registered,
 * if not sends to the NoDevice page,
 * if yes send to the DevicesList page.
 */
@Composable
fun DevicePage(navController: NavController) {
    val shRetrofitViewModel: SHRetrofitViewModel = viewModel()
    val devices = shRetrofitViewModel.getAllDevices().observeAsState()//começa a 0 ou null

    if (devices.value != null) {
        var foundRegisterDevice = false
        devices.value?.forEach {
            if (it.Nome != "") {
                foundRegisterDevice = true
            }
        }
        if (foundRegisterDevice) {
            DevicesList()
        } else {
            NoDevice(navController = navController)
        }
    } else {
        NoDevice(navController = navController)
    }


}

/**
 * Page for when there are no devices with name.
 */
@Composable
fun NoDevice(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Text(text = "Devices", fontSize = 30.sp, color = MaterialTheme.colors.primary)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 0.dp, 0.dp, 75.dp)
    ) {
        Text(text = "No devices yet. Please add a new one.", color = MaterialTheme.colors.primary)
        Button(
            onClick = { navController.navigate("addDevice") },
            modifier = Modifier.size(width = 240.dp, height = 40.dp)
        ) {
            Text(text = "Add Device")
        }
    }
}

/**
 * Page that lists all device registered.
 */
@Composable
fun DevicesList() {
    val shRetrofitViewModel: SHRetrofitViewModel = viewModel()
    val devices = shRetrofitViewModel.getAllDevices().observeAsState()//começa a 0 ou null

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(0.dp, 0.dp, 0.dp, 75.dp)
    ) {
        Text(text = "Devices", fontSize = 30.sp, color = MaterialTheme.colors.primary)
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            devices.value?.forEach {
                BoxDevices(it, shRetrofitViewModel)
            }

        }
    }
}

/**
 * A box for each type of device, with a button to turn on or off, and a button for more actions.
 */
@Composable
fun BoxDevices(shDevice: SHDevice, shRetrofitViewModel: SHRetrofitViewModel) {
    if (shDevice.Nome != "") {
        //selected is for change the button color when selected and turn on or off the device
        var selected by remember { mutableStateOf(false) }
        val color = if (selected) Color.Red else MaterialTheme.colors.secondary
        val iconBlinds = if (selected) R.drawable.ic_baseline_arrow_downward_24 else R.drawable.ic_baseline_arrow_upward_24
        var detail by remember { mutableStateOf(false) }
        val iconDetail = if (detail) R.drawable.ic_baseline_keyboard_arrow_down_24 else R.drawable.ic_baseline_keyboard_arrow_up_24
        if (selected) notificationTimer(shDevice,"start" ) else notificationTimer(shDevice,"restart" )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colors.surface)
                .size(width = 350.dp, height = 100.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .size(width = 330.dp, height = 100.dp)
                    .offset(10.dp, 0.dp)
            ) {
                if (shDevice.Tipo == "lights") {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colors.surface)
                            .clickable {
                                detail = !detail
                            }
                    ) {
                        Row() {
                            Icon(
                                painter = painterResource(R.drawable.ic_outline_lightbulb_24),
                                tint = MaterialTheme.colors.secondary,
                                contentDescription = "Light icon",
                                modifier = Modifier
                                    .size(60.dp)
                            )
                            Icon(
                                painter = painterResource(iconDetail),
                                tint = MaterialTheme.colors.secondary,
                                contentDescription = "little arrow icon",
                                modifier = Modifier
                                    .size(30.dp)
                            )
                        }
                    }
                    Text(
                        shDevice.Nome,
                        fontSize = 30.sp,
                        color = MaterialTheme.colors.primary
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_power_settings_new_24),
                        tint = color,
                        contentDescription = "Power",
                        modifier = Modifier
                            .size(60.dp)
                            .clickable {
                                //turn the device on or off based on selected
                                if (selected) {
                                    shRetrofitViewModel.getDevicesUrl("http://${shDevice.Ip}:${shDevice.Port}/light/0?turn=off")
                                } else {
                                    shRetrofitViewModel.getDevicesUrl("http://${shDevice.Ip}:${shDevice.Port}/light/0?turn=on")
                                }
                                selected = !selected
                            }
                    )
                } else if (shDevice.Tipo == "relays") {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colors.surface)
                            .clickable {
                                detail = !detail
                            }
                    ) {
                        Row() {
                            Icon(
                                painter = painterResource(R.drawable.ic_outline_settings_input_svideo_24),
                                tint = MaterialTheme.colors.secondary,
                                contentDescription = "Plug icon",
                                modifier = Modifier
                                    .size(60.dp)
                            )
                            Icon(
                                painter = painterResource(iconDetail),
                                tint = MaterialTheme.colors.secondary,
                                contentDescription = "little arrow icon",
                                modifier = Modifier
                                    .size(30.dp)
                            )
                        }
                    }
                    Text(
                        shDevice.Nome,
                        fontSize = 30.sp,
                        color = MaterialTheme.colors.primary
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_power_settings_new_24),
                        tint = color,
                        contentDescription = "Power",
                        modifier = Modifier
                            .size(60.dp)
                            .clickable {
                                //turn the device on or off based on selected
                                if (selected) {
                                    shRetrofitViewModel.getDevicesUrl("http://${shDevice.Ip}:${shDevice.Port}/relay/0?turn=off")
                                } else {
                                    shRetrofitViewModel.getDevicesUrl("http://${shDevice.Ip}:${shDevice.Port}/relay/0?turn=on")
                                }
                                selected = !selected
                            }
                    )
                } else if (shDevice.Tipo == "rollers") {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colors.surface)
                            .clickable {
                                detail = !detail
                            }
                    ) {
                        Row() {
                            Icon(
                                painter = painterResource(R.drawable.ic_outline_blinds_24),
                                tint = MaterialTheme.colors.secondary,
                                contentDescription = "Blinds icon",
                                modifier = Modifier
                                    .size(60.dp)
                            )
                            Icon(
                                painter = painterResource(iconDetail),
                                tint = MaterialTheme.colors.secondary,
                                contentDescription = "little arrow icon",
                                modifier = Modifier
                                    .size(30.dp)
                            )
                        }
                    }
                    Text(
                        shDevice.Nome,
                        fontSize = 30.sp,
                        color = MaterialTheme.colors.primary
                    )
                    Icon(
                        painter = painterResource(id = iconBlinds),
                        tint = MaterialTheme.colors.secondary,
                        contentDescription = "Up/Down icon",
                        modifier = Modifier
                            .size(60.dp)
                            .clickable {
                                //turn the device on or off based on selected
                                if (selected) {
                                    shRetrofitViewModel.getDevicesUrl("http://${shDevice.Ip}:${shDevice.Port}/roller/0?go=close")
                                } else {
                                    shRetrofitViewModel.getDevicesUrl("http://${shDevice.Ip}:${shDevice.Port}/roller/0?go=open")
                                }
                                selected = !selected
                            }
                    )
                }
            }
        }
        if (detail) DetailDevices(shDevice, shRetrofitViewModel)
    }
}


fun DeleteDeviceFromFireStore(port:String){
    val db = Firebase.firestore
    /*
    val result = db.collection("devices").whereEqualTo("port",port)
    val out = result.get().result.documents

    for (i in result.indices) {
        Log.d("REFERENCE ->gfsd", result[i].reference.toString())
    }
    */

    db.collection("devices")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                if(document.data.containsValue(port)){
                    Log.d("DOCUMENT DELETED ->", "${document.id} => ${document.data}")
                    db.collection("devices").document(document.id).delete()
                }

            }
        }
        .addOnFailureListener { exception ->
            Log.d("DOCUMENT RESULT", "Error getting documents: ", exception)
        }

}

/**
 * A box for the extras actions on a device depending on the type of device.
 */
@Composable
fun DetailDevices(shDevice: SHDevice, shRetrofitViewModel: SHRetrofitViewModel) {
    if (shDevice.Tipo == "lights") {
        var whiteMode by remember { mutableStateOf(false) }
        var colorMode by remember { mutableStateOf(false) }
        var delete by remember { mutableStateOf(false) }
        var deleteRoom by remember { mutableStateOf(false) }
        Box(
            Modifier
                .border(width = 2.dp, color = MaterialTheme.colors.surface)
                .width(350.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Select Mode",
                    fontSize = 25.sp,
                    fontFamily = quickSand,
                    color = MaterialTheme.colors.secondary
                )
                Button(
                    onClick = {
                        whiteMode = !whiteMode
                    },
                ) {
                    Text(text = "White Mode")
                }
                if (whiteMode) DetailLights(shDevice,shRetrofitViewModel,true)
                Button(
                    onClick = {
                        colorMode = !colorMode
                    },
                ) {
                    Text(text = "Color Mode")
                }
                if (colorMode) DetailLights(shDevice,shRetrofitViewModel,false)
                Button(
                    onClick = {
                        delete = true
                    },
                ) {
                    Text(text = "Delete local device")
                }
                if (delete) {
                    shRetrofitViewModel.deleteOne(shDevice.Ip,shDevice.Port)
                }
                Button(
                    onClick = {
                        deleteRoom = true
                    },
                ) {
                    Text(text = "Delete device")
                }
                if (deleteRoom) {
                    DeleteDeviceFromFireStore(shDevice.Port)
                }
                /** SHARE DEVICE **/
                val (email, onEmailChange) = remember { mutableStateOf("") }
                    Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = onEmailChange,
                        label = { Text(text = "email") },
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            focusedLabelColor = MaterialTheme.colors.secondary,
                            unfocusedLabelColor = MaterialTheme.colors.secondary,
                        )
                    )
                }
                Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp))
                Row() {
                    Button(
                        onClick = {
                            shareDevice(email, shDevice)
                        },
                        modifier = Modifier.size(width = 240.dp, height = 40.dp)
                    ) {
                        Text(text = "Share")
                    }
                }
                Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp))
                /** SHARE DEVICE **/
            }
        }
    } else if (shDevice.Tipo == "relays") {
        val (numMin, onNomeChange) = remember { mutableStateOf("") }
        var delete by remember { mutableStateOf(false) }
        var deleteRoom by remember { mutableStateOf(false) }
        Box(
            Modifier
                .border(width = 2.dp, color = MaterialTheme.colors.surface)
                .width(350.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Turn on for limited time",
                    fontSize = 20.sp,
                    fontFamily = quickSand,
                    color = MaterialTheme.colors.secondary
                )
                Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_outline_settings_input_svideo_24),
                        tint = MaterialTheme.colors.secondary,
                        contentDescription = "Add Device",
                        modifier = Modifier
                            .size(50.dp)
                    )
                    Spacer(modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp))
                    OutlinedTextField(
                        value = numMin,
                        onValueChange = onNomeChange,
                        label = { Text(text = "insert minutes") },
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            focusedLabelColor = MaterialTheme.colors.secondary,
                            unfocusedLabelColor = MaterialTheme.colors.secondary,
                        )
                    )
                }
                Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp))
                Row() {
                    Button(
                        onClick = {
                            shRetrofitViewModel.getDevicesUrl("http://${shDevice.Ip}:${shDevice.Port}/relay/0?turn=on&duration=$numMin")

                        },
                        modifier = Modifier.size(width = 240.dp, height = 40.dp)
                    ) {
                        Text(text = "Confirm")
                    }
                }
                Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp))
                Row() {
                    Button(
                        onClick = {
                            delete = true
                        },
                    ) {
                        Text(text = "Delete local device")
                    }
                    if (delete) {shRetrofitViewModel.deleteOne(shDevice.Ip,shDevice.Port)
                    }
                }
                Button(
                    onClick = {
                        deleteRoom = true
                    },
                ) {
                    Text(text = "Delete device")
                }
                if (deleteRoom) {
                    DeleteDeviceFromFireStore(shDevice.Port)
                }
                /** SHARE DEVICE **/
                val (email, onEmailChange) = remember { mutableStateOf("") }
                Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = onEmailChange,
                        label = { Text(text = "email") },
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            focusedLabelColor = MaterialTheme.colors.secondary,
                            unfocusedLabelColor = MaterialTheme.colors.secondary,
                        )
                    )
                }
                Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp))
                Row() {
                    Button(
                        onClick = {
                            shareDevice(email, shDevice)
                        },
                        modifier = Modifier.size(width = 240.dp, height = 40.dp)
                    ) {
                        Text(text = "Share")
                    }
                }
                Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp))
                /** SHARE DEVICE **/
            }
        }
    } else if (shDevice.Tipo == "rollers") {
        var sliderPosition by remember { mutableStateOf(0.0f) }
        var delete by remember { mutableStateOf(false) }
        var deleteRoom by remember { mutableStateOf(false) }
        Box(
            Modifier
                .border(width = 2.dp, color = MaterialTheme.colors.surface)
                .width(350.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Select blinds position",
                    fontSize = 25.sp,
                    fontFamily = quickSand,
                    color = MaterialTheme.colors.secondary
                )
                Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp))
                Text(text = sliderPosition.toInt().toString())
                Slider(
                    value = sliderPosition,
                    valueRange = 0f..100f,
                    onValueChange = { sliderPosition = it })
                Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp))
                Row() {
                    Button(
                        onClick = {
                            var openPercent = sliderPosition.toInt().toString()
                            shRetrofitViewModel.getDevicesUrl("http://${shDevice.Ip}:${shDevice.Port}/roller/0?go=to_pos&roller_pos=$openPercent")
                        },
                        modifier = Modifier.size(width = 240.dp, height = 40.dp)
                    ) {
                        Text(text = "Confirm")
                    }
                }
                Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp))
                Row() {
                    Button(
                        onClick = {
                            delete = true
                        },
                    ) {
                        Text(text = "Delete local device")
                    }
                    if (delete) {
                        shRetrofitViewModel.deleteOne(shDevice.Ip,shDevice.Port)
                    }
                }
                Button(
                    onClick = {
                        deleteRoom = true
                    },
                ) {
                    Text(text = "Delete device")
                }
                if (deleteRoom) {
                    DeleteDeviceFromFireStore(shDevice.Port)
                }
                /** SHARE DEVICE **/
                val (email, onEmailChange) = remember { mutableStateOf("") }
                Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = onEmailChange,
                        label = { Text(text = "email") },
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            focusedLabelColor = MaterialTheme.colors.secondary,
                            unfocusedLabelColor = MaterialTheme.colors.secondary,
                        )
                    )
                }
                Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp))
                Row() {
                    Button(
                        onClick = {
                            shareDevice(email, shDevice)
                        },
                        modifier = Modifier.size(width = 240.dp, height = 40.dp)
                    ) {
                        Text(text = "Share")
                    }
                }
                Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp))
                /** SHARE DEVICE **/
            }
        }
    }
}

/**
 * Box for each mode of the lights.
 */
@Composable
fun DetailLights(shDevice: SHDevice, shRetrofitViewModel: SHRetrofitViewModel,mode:Boolean) {
    if(mode){
        var sliderWhitePosition by remember { mutableStateOf(0.0f) }
        var sliderBrightPosition by remember { mutableStateOf(0.0f) }
        Box(
            Modifier
                .border(width = 2.dp, color = MaterialTheme.colors.surface)
                .width(350.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row() {
                    Text(text = "White:")
                    Text(text = sliderWhitePosition.toInt().toString())
                }
                Slider(
                    value = sliderWhitePosition,
                    valueRange = 0f..100f,
                    onValueChange = { sliderWhitePosition = it })
                Row() {
                    Text(text = "Brightness:")
                    Text(text = sliderBrightPosition.toInt().toString())
                }
                Slider(
                    value = sliderBrightPosition,
                    valueRange = 0f..100f,
                    onValueChange = { sliderBrightPosition = it })
                Row() {
                    Button(
                        onClick = {
                            var white = sliderWhitePosition.toInt().toString()
                            var bright = sliderBrightPosition.toInt().toString()
                            shRetrofitViewModel.getDevicesUrl("http://${shDevice.Ip}:${shDevice.Port}/light/0?turn=on&mode=white&white=$white&brightness=$bright")
                        },
                        modifier = Modifier.size(width = 240.dp, height = 40.dp)
                    ) {
                        Text(text = "Confirm")
                    }
                }
                Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp))
            }
        }
    }else{
        var sliderRedPosition by remember { mutableStateOf(0.0f) }
        var sliderGreenPosition by remember { mutableStateOf(0.0f) }
        var sliderBluePosition by remember { mutableStateOf(0.0f) }
        var sliderGainPosition by remember { mutableStateOf(0.0f) }
        Box(
            Modifier
                .border(width = 2.dp, color = MaterialTheme.colors.surface)
                .width(350.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row() {
                    Text(text = "Red:")
                    Text(text = sliderRedPosition.toInt().toString())
                }
                Slider(
                    value = sliderRedPosition,
                    valueRange = 0f..255f,
                    onValueChange = { sliderRedPosition = it })
                Row() {
                    Text(text = "Green:")
                    Text(text = sliderGreenPosition.toInt().toString())
                }
                Slider(
                    value = sliderGreenPosition,
                    valueRange = 0f..100f,
                    onValueChange = { sliderGreenPosition = it })
                Row() {
                    Text(text = "Blue:")
                    Text(text = sliderBluePosition.toInt().toString())
                }
                Slider(
                    value = sliderBluePosition,
                    valueRange = 0f..100f,
                    onValueChange = { sliderBluePosition = it })
                Row() {
                    Text(text = "Gain:")
                    Text(text = sliderGainPosition.toInt().toString())
                }
                Slider(
                    value = sliderGainPosition,
                    valueRange = 0f..100f,
                    onValueChange = { sliderGainPosition = it })
                Row() {
                    Button(
                        onClick = {
                            var red = sliderRedPosition.toInt().toString()
                            var green = sliderGreenPosition.toInt().toString()
                            var blue = sliderBluePosition.toInt().toString()
                            var gain = sliderGainPosition.toInt().toString()
                            shRetrofitViewModel.getDevicesUrl("http://${shDevice.Ip}:${shDevice.Port}/light/0?turn=on&mode=color&red=$red&green=$green&blue=$blue&gain=$gain")
                        },
                        modifier = Modifier.size(width = 240.dp, height = 40.dp)
                    ) {
                        Text(text = "Confirm")
                    }
                }
                Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp))
            }
        }
    }
}


@Composable
fun notificationTimer(shDevice: SHDevice, estado :String){
    val context = LocalContext.current
    val totalTime = 15000L
    var currentTime by remember {
        mutableStateOf(totalTime)
    }
    var isTimerRunning by remember {
        mutableStateOf(false)
    }
    if(estado == "start"){
        isTimerRunning = true
    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning){
        if(currentTime > 0 && isTimerRunning){
            delay(100L)
            currentTime -= 100L
        }else if(currentTime == 0L){
            val notify = DeviceNotification(context,shDevice)
            notify.fireNotification()
        }
    }
    }else if(estado == "restart"){
        isTimerRunning = false
        currentTime = totalTime
    }
}

fun shareDevice(userEmail: String, device: SHDevice){
    val db = Firebase.firestore
    val data = hashMapOf("shared" to userEmail)

    db.collection("devices")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                if(document.data.containsValue(device.Mac)){
                    Log.d("DEVICE TO BE SHARED ->", "${document.id} => ${document.data}")
                    db.collection("devices").document(document.id).set(
                        data,
                        SetOptions.merge()
                    )
                }

            }
        }
        .addOnFailureListener { exception ->
            Log.d("DOCUMENT RESULT", "Error getting documents: ", exception)
        }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    AppTheme(darkTheme = false) {
        DevicesList()
    }
}