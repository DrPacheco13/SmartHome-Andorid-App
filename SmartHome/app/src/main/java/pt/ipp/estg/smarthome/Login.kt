package pt.ipp.estg.smarthome

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.common.io.Resources
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pt.ipp.estg.smarthome.common.database.UserViewModel
import pt.ipp.estg.smarthome.ui.theme.AppTheme
import pt.ipp.estg.smarthome.ui.theme.quickSand

class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
        }
    }
}

/**
 * Sign In part
 */
@Composable
fun loginUI(navController : NavController) {
    val (email, onEmailChange) = remember { mutableStateOf("") }
    val (password, onPassChange) = remember { mutableStateOf("") }
    val userViewModel: UserViewModel = viewModel()
    val context = LocalContext.current

    Text(
        text = "Sign In",
        fontSize = 40.sp,
        fontFamily = quickSand,
        color = MaterialTheme.colors.secondary
    )
    Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp))
    TextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text(text = "Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            focusedLabelColor = MaterialTheme.colors.secondary,
            unfocusedLabelColor = MaterialTheme.colors.secondary,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
    Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 5.dp))
    Divider(modifier = Modifier.padding(50.dp, 0.dp), color = Color.White)
    TextField(
        value = password,
        onValueChange = onPassChange,
        label = { Text(text = "Password") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            focusedLabelColor = MaterialTheme.colors.secondary,
            unfocusedLabelColor = MaterialTheme.colors.secondary,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
    Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 5.dp))
    Divider(modifier = Modifier.padding(50.dp, 0.dp), color = Color.White)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.padding(0.dp, 50.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            val userCheck = userViewModel.findUserEmail(email).observeAsState()
            val encryptedPassword = encryptPass(password, true)
            val conn : Boolean = connectionChecker(context)

            Button(
                enabled = true,
                onClick = {
                    if (conn){
                        //FIREBASE LOGIN if it has internet connection
                        loginFirebase(email, password, context, userViewModel, navController)
                    } else {
                        //ROOM LOGIN if it hasn't internet connection
                        if (userCheck.value?.password == encryptedPassword) {
                            userViewModel.updateLoggInStatus(logIn = true, email = email)
                            emailLoggedIn = email
                            Log.d(" Room Debug: ", emailLoggedIn)
                            navController.navigate("listDevices")
                        } else {
                            Toast.makeText(
                                context,
                                "Email/password Incorrect or User doesn't exist",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
            ) {
                Text(text = "Continue")
            }
            TextButton(
                enabled = true,
                onClick = { navController.navigate("signUp") }
            ) {
                Text(
                    text = context.getString(R.string.not_have_an_account),
                    color = MaterialTheme.colors.secondary
                )
            }
        }
    }
}

fun loginFirebase(email: String, password: String, ctx: Context, userViewModel: UserViewModel, navController: NavController){
    lateinit var auth: FirebaseAuth

    if (email.isNotEmpty() && password.isNotEmpty()) {
        auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userViewModel.updateLoggInStatus(logIn = true, email = email)
                emailLoggedIn = email
                navController.navigate("listDevices")
                Toast.makeText(
                    ctx,
                    "Successfully Logged In",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    ctx,
                    "Login Failed. Please check email / password",
                    Toast.LENGTH_LONG
                )
                    .show()
                Log.w("LOGIN:failure", task.exception);
                navController.navigate("login")
            }
        }
    }else{
        Toast.makeText(
            ctx,
            "Please insert email / password",
            Toast.LENGTH_SHORT
        ).show()
        navController.navigate("login")
    }
}

/**
 * Checks for a connection to internet
 */
@Composable
fun connectionChecker(
    context: Context,
) : Boolean {
    val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connManager.getNetworkCapabilities(connManager.activeNetwork)

    return if (networkCapabilities == null) {
        Log.d("Login", "Device Offline")
        false
    } else {
        Log.d("Login", "Device Online")
        true
    }
}

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    AppTheme(darkTheme = isDark.value) {
        loginUI()
    }
}
*/