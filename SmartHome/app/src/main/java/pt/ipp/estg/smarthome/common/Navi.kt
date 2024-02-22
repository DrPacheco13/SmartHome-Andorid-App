package pt.ipp.estg.smarthome.common

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pt.ipp.estg.smarthome.*
import kotlin.system.exitProcess

@Composable
fun Navigation() {
    var isLogin: Boolean
    var isSignUp: Boolean
    var isSignUpIn: Boolean
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            isLogin = true
            isSignUp = false
            isSignUpIn = isLogin or isSignUp
            MyScaffold(
                navController = navController,
                contFunc = {
                    CreateAccount(
                        navController = navController,
                        isLogin = isLogin
                    )
                },
                bottFunc = {
                    MyBottomBar(
                        navController = navController,
                        selected = false
                    )
                },
                isSignUpIn = isSignUpIn
            )
        }
        composable("signUp") {
            isLogin = false
            isSignUp = true
            isSignUpIn = isLogin or isSignUp
            MyScaffold(
                navController = navController,
                contFunc = {
                    CreateAccount(
                        navController = navController,
                        isLogin = isLogin
                    )
                },
                bottFunc = {
                    MyBottomBar(
                        navController = navController,
                        selected = false
                    )
                },
                isSignUpIn = isSignUpIn
            )
        }
        composable("listDevices") {
            isLogin = false
            isSignUp = false
            isSignUpIn = isLogin or isSignUp
            MyScaffold(
                navController = navController,
                contFunc = { DevicePage(navController = navController) },
                bottFunc = {
                    MyBottomBar(
                        navController = navController,
                        selected = true
                    )
                },
                isSignUpIn = isSignUpIn
            )
        }
        composable("addDevice"){
            isLogin = false
            isSignUp = false
            isSignUpIn = isLogin or isSignUp
            MyScaffold(
                navController = navController,
                contFunc = { ChoseDeviceType(navController = navController) },
                bottFunc = {
                    MyBottomBar(
                        navController = navController,
                        selected = true
                    )
                },
                isSignUpIn = isSignUpIn
            )
        }
        composable("listGroups"){
            isLogin = false
            isSignUp = false
            isSignUpIn = isLogin or isSignUp
            MyScaffold(
                navController = navController,
                contFunc = { listGroups() },
                bottFunc = {
                    MyBottomBar(
                        navController = navController,
                        selected = true
                    )
                },
                isSignUpIn = isSignUpIn
            )
        }
        composable("mapNav"){
            isLogin = false
            isSignUp = false
            isSignUpIn = isLogin or isSignUp
            MyScaffold(
                navController = navController,
                contFunc = { currLocMap() },
                bottFunc = {
                    MyBottomBar(
                        navController = navController,
                        selected = true
                    )
                },
                isSignUpIn = isSignUpIn
            )
        }
        composable("newDeviceLight"){
            isLogin = false
            isSignUp = false
            isSignUpIn = isLogin or isSignUp
            MyScaffold(
                navController = navController,
                contFunc = { ListAllDevicesByType("lights",navController = navController) },
                bottFunc = {
                    MyBottomBar(
                        navController = navController,
                        selected = true
                    )
                },
                isSignUpIn = isSignUpIn
            )
        }
        composable("newDevicePlug"){
            isLogin = false
            isSignUp = false
            isSignUpIn = isLogin or isSignUp
            MyScaffold(
                navController = navController,
                contFunc = { ListAllDevicesByType("relays",navController = navController) },
                bottFunc = {
                    MyBottomBar(
                        navController = navController,
                        selected = true
                    )
                },
                isSignUpIn = isSignUpIn
            )
        }
        composable("newDeviceBlinds"){
            isLogin = false
            isSignUp = false
            isSignUpIn = isLogin or isSignUp
            MyScaffold(
                navController = navController,
                contFunc = { ListAllDevicesByType("rollers",navController = navController) },
                bottFunc = {
                    MyBottomBar(
                        navController = navController,
                        selected = true
                    )
                },
                isSignUpIn = isSignUpIn
            )
        }
    }
}