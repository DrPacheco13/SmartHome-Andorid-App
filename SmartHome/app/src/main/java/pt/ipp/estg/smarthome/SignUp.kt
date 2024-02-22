package pt.ipp.estg.smarthome

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalContext
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.scottyab.aescrypt.AESCrypt
import pt.ipp.estg.smarthome.common.database.UserViewModel
import pt.ipp.estg.smarthome.ui.theme.AppTheme
import pt.ipp.estg.smarthome.ui.theme.quickSand
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import pt.ipp.estg.smarthome.common.CommonValues
import pt.ipp.estg.smarthome.common.database.User

class SignUp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
        }
    }
}

@Composable
fun CreateAccount(navController: NavController, isLogin: Boolean = true) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            if (!isLogin) {
                /**
                 * Sign Up part
                 */
                signUpUI(navController = navController)
            } else {
                /**
                 * Sign In part
                 */
                loginUI(navController = navController)
            }
        }
    }
}

var emailLoggedIn: String = ""

/**
 * Sign Up part
 */
@Composable
fun signUpUI(navController: NavController) {
    val (email, onEmailChange) = remember { mutableStateOf("") }
    val (password, onPassChange) = remember { mutableStateOf("") }
    val (confPass, onConfPassChange) = remember { mutableStateOf("") }
    val userViewModel: UserViewModel = viewModel()
    val context = LocalContext.current

    Text(
        text = "Sign Up",
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
    TextField(
        value = confPass,
        onValueChange = onConfPassChange,
        label = { Text(text = "Confirm Password") },
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
            Button(
                enabled = true,
                onClick = {
                    if (password == confPass && password.length > 5) {
                        //ROOM CREATE NEW USER
                        userViewModel.insertUser(
                            User(
                                email = email,
                                password = encryptPass(pass = password, encrypt = true),
                                loggedIn = true
                            )
                        )
                        //FIREBASE CREATE USER
                        createUserFirebase(email, password, context)

                        emailLoggedIn = email
                        navController.navigate("listDevices")
                    } else {
                        Toast.makeText(
                            context,
                            "Passwords don't match or length smaller than 6 characters",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
            ) {
                Text(text = "Continue")
            }
            TextButton(
                enabled = true,
                onClick = { navController.navigate("login") }
            ) {
                Text(
                    text = context.getString(R.string.already_have_an_account),
                    color = MaterialTheme.colors.secondary
                )
            }
        }
    }
}

/**
 * Encrypts/Decrypts password
 *
 * @param pass is the pass to encrypt/decrypt
 * @param encrypt if true -> encrypts, else, decrypts
 */
fun encryptPass(pass: String, encrypt: Boolean): String {
    val key = CommonValues.EncryptionKey.key

    return if (encrypt) {
        AESCrypt.encrypt(key, pass)
    } else {
        AESCrypt.decrypt(key, pass)
    }
}

fun createUserFirebase(email: String, password: String, ctx: Context) {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(ctx, "Successfully Registered", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(ctx, "Registration Failed", Toast.LENGTH_LONG).show()
                Log.w("createUserWithEmail:failure", task.exception);
            }
        }
}

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme(darkTheme = true) {
        CreateAccount(false)
    }
}
*/