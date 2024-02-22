package pt.ipp.estg.smarthome

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import pt.ipp.estg.smarthome.Retrofit.Room.SHDevice
import pt.ipp.estg.smarthome.Retrofit.SHRetrofitViewModel
import pt.ipp.estg.smarthome.ui.theme.AppTheme
import pt.ipp.estg.smarthome.ui.theme.quickSand

class ChoseDevice : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

/**
 * A basic list of the types of devices for the user to chose
 */
@Composable
fun ChoseDeviceType(navController: NavController) {
    var manual by remember { mutableStateOf(false) }
    var refresh by remember { mutableStateOf(false) }
    if (refresh) RetrofitStart()
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState())
            .padding(0.dp, 0.dp, 0.dp, 70.dp)
    ) {
        Row() {
            Text(text = "Select a Device", fontSize = 30.sp,color = MaterialTheme.colors.primary)
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_refresh_24),
                tint = MaterialTheme.colors.primary,
                contentDescription = "Power",
                modifier = Modifier
                    .size(60.dp)
                    .clickable { retrofitOnce = true
                        refresh = !refresh }
            )
        }
        BoxDevice("Light",navController = navController)
        BoxDevice("Plug",navController = navController)
        BoxDevice("Blinds",navController = navController)
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colors.surface)
                .size(width = 350.dp, height = 100.dp)
                .clickable { manual = !manual }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(30.dp ,Alignment.Start),
                modifier = Modifier
                    .size(width = 200.dp, height = 100.dp)
                    .offset(75.dp, 0.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add_pl),
                    tint = MaterialTheme.colors.background,
                    contentDescription = "type of device",
                    modifier = Modifier
                        .size(60.dp)
                )
                Text("Manual", fontSize = 30.sp,color = MaterialTheme.colors.primary)

            }
        }
        if (manual) NameManualDevices(navController)
    }
}

/**
 * A box that varies based on the type of the device, for the list above
 */
@Composable
fun BoxDevice(nome : String,navController: NavController){
    var id = R.drawable.ic_add_pl
    var navLink = ""
    if (nome == "Light"){
        id = R.drawable.ic_outline_lightbulb_24
        navLink = "newDeviceLight"
    }else if (nome == "Plug"){
        id = R.drawable.ic_outline_settings_input_svideo_24
        navLink = "newDevicePlug"
    }else if (nome == "Blinds"){
        id = R.drawable.ic_outline_blinds_24
        navLink = "newDeviceBlinds"
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colors.surface)
            .size(width = 350.dp, height = 100.dp)
            .clickable { navController.navigate(navLink) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(30.dp ,Alignment.Start),
            modifier = Modifier
                .size(width = 200.dp, height = 100.dp)
                .offset(75.dp, 0.dp)
        ) {
            Icon(
                painter = painterResource(id),
                tint = MaterialTheme.colors.background,
                contentDescription = "type of device",
                modifier = Modifier
                    .size(60.dp)
            )
            Text(nome, fontSize = 30.sp,color = MaterialTheme.colors.primary)

        }
    }
}

/**
 * Displays a list of all devices that are not registered of a certain type
 */
@Composable
fun ListAllDevicesByType(nome : String,navController: NavController) {
    val shRetrofitViewModel: SHRetrofitViewModel = viewModel()
    val devices = shRetrofitViewModel.getAllDevices().observeAsState()//começa a 0 ou null
    var unknownDevices = false

    if (devices.value != null) {
        devices.value?.forEach {
            if (it.Nome == "") {
                if (it.Tipo == nome) {
                    unknownDevices = true
                }
            }
        }
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .padding(0.dp, 0.dp, 0.dp, 75.dp)
            ) {
                if (unknownDevices) {
                Text(text = "Devices", fontSize = 30.sp, color = MaterialTheme.colors.primary)
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                    devices.value?.forEach {
                        var selected by remember { mutableStateOf(false) }
                        if (it.Nome == "") {
                            if (it.Tipo == nome) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(MaterialTheme.colors.surface)
                                        .size(width = 350.dp, height = 100.dp)
                                        .clickable { selected = !selected }
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .size(width = 330.dp, height = 100.dp)
                                            .offset(10.dp, 0.dp)
                                    ) {
                                        if (it.Tipo == "lights") {
                                            Icon(
                                                painter = painterResource(R.drawable.ic_outline_lightbulb_24),
                                                tint = MaterialTheme.colors.secondary,
                                                contentDescription = "Light icon",
                                                modifier = Modifier
                                                    .size(60.dp)
                                            )
                                            Text(
                                                it.Mac,
                                                fontSize = 30.sp,
                                                color = MaterialTheme.colors.primary
                                            )
                                        } else if (it.Tipo == "relays") {
                                            Icon(
                                                painter = painterResource(R.drawable.ic_outline_settings_input_svideo_24),
                                                tint = MaterialTheme.colors.secondary,
                                                contentDescription = "Plug icon",
                                                modifier = Modifier
                                                    .size(60.dp)
                                            )
                                            Text(
                                                it.Mac,
                                                fontSize = 30.sp,
                                                color = MaterialTheme.colors.primary
                                            )
                                        } else if (it.Tipo == "rollers") {
                                            Icon(
                                                painter = painterResource(R.drawable.ic_outline_blinds_24),
                                                tint = MaterialTheme.colors.secondary,
                                                contentDescription = "Blinds icon",
                                                modifier = Modifier
                                                    .size(60.dp)
                                            )
                                            Text(
                                                it.Mac,
                                                fontSize = 30.sp,
                                                color = MaterialTheme.colors.primary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        if (selected) NameDevices(it.Port,navController)
                    }
                }
                } else {
                    Text(text = "No devices found yet...", fontSize = 30.sp, color = MaterialTheme.colors.primary)
                }
            }

    }
}


fun AddDeviceToFireStore(device: SHDevice){
    val db = Firebase.firestore
    Log.d("EMAIL LOGGED ->", emailLoggedIn)
    var docId = "";
    val user = hashMapOf("Users" to emailLoggedIn)

    db.collection("devices").add(device)
        .addOnSuccessListener { documentReference ->
            docId = documentReference.id;
            Log.d("INSERT FIRESTORE","DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w("INSERT FIRESTORE", "Error adding document", e)
        }

    //db.collection("devices").document(docId).set({Users:"xpto"},{merge:true})
}

/**
 * A box with the icon of the device, and a Text field to name a device and register it
 */
@Composable
fun NameDevices(port : String,navController: NavController) {
    val shRetrofitViewModel: SHRetrofitViewModel = viewModel()
    val devices = shRetrofitViewModel.getAllDevices().observeAsState()//começa a 0 ou null
    devices.value?.forEach {
        if (it.Port == port) {
            val (nome, onNomeChange) = remember { mutableStateOf("") }
            var id = R.drawable.ic_add_pl
            if (it.Tipo == "lights") {
                id = R.drawable.ic_outline_lightbulb_24
            } else if (it.Tipo == "relays") {
                id = R.drawable.ic_outline_settings_input_svideo_24
            } else if (it.Tipo == "rollers") {
                id = R.drawable.ic_outline_blinds_24
            }
            Box(
                Modifier
                    .border(width = 2.dp, color = MaterialTheme.colors.surface)
                    .size(width = 350.dp, height = 200.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                Text(
                    text = "Name Device",
                    fontSize = 40.sp,
                    fontFamily = quickSand,
                    color = MaterialTheme.colors.secondary
                )
                Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        painter = painterResource(id),
                        tint = MaterialTheme.colors.secondary,
                        contentDescription = "Type of device",
                        modifier = Modifier
                            .size(50.dp)
                    )
                    Spacer(modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp))
                    OutlinedTextField(
                        value = nome,
                        onValueChange = onNomeChange,
                        label = { Text(text = it.Mac) },
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
                            if(nome != "") {
                                shRetrofitViewModel.updateDevice(
                                    SHDevice(
                                        it.Port,
                                        it.Ip,
                                        nome,
                                        it.Tipo,
                                        it.Mac,
                                    )
                                )
                                AddDeviceToFireStore(SHDevice(
                                    it.Port,
                                    it.Ip,
                                    nome,
                                    it.Tipo,
                                    it.Mac,
                                ))
                                navController.navigate("listDevices")
                            }
                        },
                        modifier = Modifier.size(width = 240.dp, height = 40.dp)
                    ) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }
}
}

@Composable
fun NameManualDevices(navController: NavController) {
    val shRetrofitViewModel: SHRetrofitViewModel = viewModel()
    val devices = shRetrofitViewModel.getAllDevices().observeAsState()//começa a 0 ou null

            val (nome, onNomeChange) = remember { mutableStateOf("") }
            val (ip, onIpChange) = remember { mutableStateOf("") }
            val (port, onPortChange) = remember { mutableStateOf("") }
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
                        text = "Manual add",
                        fontSize = 40.sp,
                        fontFamily = quickSand,
                        color = MaterialTheme.colors.secondary
                    )
                    Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp))
                        Spacer(modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp))
                        OutlinedTextField(
                            value = nome,
                            onValueChange = onNomeChange,
                            label = { Text(text = "Insert name") },
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.White,
                                focusedLabelColor = MaterialTheme.colors.secondary,
                                unfocusedLabelColor = MaterialTheme.colors.secondary,
                            )
                        )
                    Spacer(modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp))
                    OutlinedTextField(
                        value = ip,
                        onValueChange = onIpChange,
                        label = { Text(text = "Insert ip") },
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            focusedLabelColor = MaterialTheme.colors.secondary,
                            unfocusedLabelColor = MaterialTheme.colors.secondary,
                        )
                    )
                    Spacer(modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp))
                    OutlinedTextField(
                        value = port,
                        onValueChange = onPortChange,
                        label = { Text(text = "Insert port") },
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            focusedLabelColor = MaterialTheme.colors.secondary,
                            unfocusedLabelColor = MaterialTheme.colors.secondary,
                        )
                    )

                    Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp))
                    Row() {
                        Button(
                            onClick = {
                                if(nome != "" && ip != "" && port != "") {
                                    val url = "http://$ip:$port"

                                    shRetrofitViewModel.getStatus(url,nome)

                                    devices.value?.forEach {
                                        if (it.Port == port && it.Ip == ip) {
                                            shRetrofitViewModel.updateDevice(
                                                SHDevice(
                                                    port,
                                                    ip,
                                                    nome,
                                                    it.Tipo,
                                                    it.Mac
                                                )
                                            )
                                            AddDeviceToFireStore(
                                                SHDevice(
                                                    port,
                                                    ip,
                                                    nome,
                                                    it.Tipo,
                                                    it.Mac
                                                )
                                            )
                                        }
                                    }
                                    navController.navigate("listDevices")
                                }
                            },
                            modifier = Modifier.size(width = 240.dp, height = 40.dp)
                        ) {
                            Text(text = "Confirm")
                        }
                    }
                }
            }
        }


@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {

}