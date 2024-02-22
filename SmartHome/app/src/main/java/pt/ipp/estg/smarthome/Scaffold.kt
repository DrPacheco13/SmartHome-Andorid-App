package pt.ipp.estg.smarthome

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pt.ipp.estg.smarthome.common.AppTitles
import pt.ipp.estg.smarthome.common.database.UserViewModel

class Scaffold : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
        }
    }
}

@Composable
fun MyScaffold(
    navController: NavController,
    contFunc: @Composable () -> Unit,
    bottFunc: @Composable () -> Unit,
    isSignUpIn: Boolean
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()
    val userViewModel: UserViewModel = viewModel()
    val ctx = LocalContext.current

    Scaffold(
        scaffoldState = scaffoldState,
        contentColor = Color.White,
        content = { contFunc() },
        topBar = {
            MyTopBar(
                navController = navController,
                scaffoldState = scaffoldState,
                scope = scope,
                isSignUpIn = isSignUpIn
            )
        },
        bottomBar = { bottFunc() },
        drawerBackgroundColor = MaterialTheme.colors.secondary,
        drawerContent = {
            Column(modifier = Modifier.fillMaxHeight()) {
                drawerContentAssist(
                    id = R.drawable.ic_outline_dark_mode_24,
                    contDesc = "Change color to night or day mode",
                    title = "Night Mode",
                    tint = MaterialTheme.colors.background,
                    onClick = { toggleLightTheme() }
                )
                drawerContentAssist(
                    id = R.drawable.ic_baseline_call_24,
                    contDesc = "Call Support, call ESTG Felgueiras",
                    title = "Call Support",
                    tint = MaterialTheme.colors.background,
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"255 314 002"))
                        ctx.startActivity(intent)
                    }
                )
                if (!isSignUpIn) {
                    drawerContentAssist(
                        id = R.drawable.ic_baseline_logout_24,
                        contDesc = "Logout",
                        title = "Logout",
                        tint = Color.Red,
                        onClick = {
                            userViewModel.updateLoggInStatus(
                                logIn = false,
                                email = emailLoggedIn
                            )
                            navController.navigate("login")
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun drawerContentAssist(
    id: Int,
    contDesc: String,
    title: String,
    tint: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .padding(16.dp)) {
        Icon(
            imageVector = ImageVector.vectorResource(id = id),
            contentDescription = contDesc,
            tint = tint
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.weight(1f),
            color = tint
        )
    }
}

@Composable
fun MyTopBar(
    navController: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    isSignUpIn: Boolean
) {
    val drawerState = scaffoldState.drawerState

    if (isSignUpIn) {
        topBarAssist(navController = navController, scope = scope, drawerState = drawerState)
    } else {
        topBarAssist(
            navController = navController,
            scope = scope,
            drawerState = drawerState,
            enableIcon = true
        )
    }
}

@Composable
fun topBarAssist(
    navController: NavController,
    scope: CoroutineScope,
    drawerState: DrawerState,
    enableIcon: Boolean = false
) {
    TopAppBar(
        modifier = Modifier.height(65.dp),
        navigationIcon = {
            IconButton(
                content = {
                    Icon(
                        Icons.Default.Menu,
                        tint = MaterialTheme.colors.secondary,
                        contentDescription = "Menu",
                        modifier = Modifier.size(30.dp)
                    )
                },
                onClick = {
                    scope.launch {
                        if (drawerState.isClosed) {
                            drawerState.open()
                        } else {
                            drawerState.close()
                        }
                    }
                }
            )
        },
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icons8_smart_home_96),
                    contentDescription = "App Icon"
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = AppTitles.AppTitle.key,
                    color = MaterialTheme.colors.primary,
                )
            }
        },
        backgroundColor = MaterialTheme.colors.background,
        actions = {
            IconButton(
                enabled = enableIcon,
                modifier = Modifier.padding(start = 19.dp),
                onClick = {
                    navController.navigate("addDevice")
                }) {
                val tint: Color = if (enableIcon) {
                    MaterialTheme.colors.secondary
                } else {
                    MaterialTheme.colors.background
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_pl),
                    tint = tint,
                    contentDescription = "Add Device",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    )
}

//Bottom bar for navigation
@Composable
fun MyBottomBar(navController: NavController, selected: Boolean) {
    bottomBarAssist(navController = navController, selected = selected)
}

@Composable
fun bottomBarAssist(
    navController: NavController,
    modifier: Modifier = Modifier,
    selected: Boolean
) {
    BottomNavigation(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp))
            .height(70.dp),
        backgroundColor = MaterialTheme.colors.secondary
    ) {
        //Home Icon
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home_pl),
                    tint = MaterialTheme.colors.background,
                    contentDescription = "Home"
                )
            },
            label = {
                Text(
                    text = AppTitles.LBHome.key,
                    color = MaterialTheme.colors.background
                )
            },
            enabled = selected,
            selected = selected,
            modifier = modifier.padding(0.dp, 5.dp), // elevates the navigation item
            onClick = {
                navController.navigate("listDevices")
            }
        )
        //Groups Icon
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_groups_pl),
                    tint = MaterialTheme.colors.background,
                    contentDescription = "Groups"
                )
            },
            label = {
                Text(
                    text = AppTitles.LBGroup.key,
                    color = MaterialTheme.colors.background
                )
            },
            enabled = selected,
            selected = selected,
            modifier = modifier.padding(0.dp, 5.dp), // elevates the navigation item
            onClick = { navController.navigate("listGroups") }
        )
        //Routine Icon
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_map_24),
                    tint = MaterialTheme.colors.background,
                    contentDescription = "Map",
                    modifier = Modifier.size(30.dp)
                )
            },
            label = {
                Text(
                    text = AppTitles.LBMap.key,
                    color = MaterialTheme.colors.background
                )
            },
            enabled = selected,
            selected = selected,
            modifier = modifier.padding(0.dp, 5.dp), // elevates the navigation item
            onClick = { navController.navigate("mapNav") }
        )
        //Account Icon
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_account_pl),
                    tint = MaterialTheme.colors.background,
                    contentDescription = "Account",
                    modifier = Modifier.size(30.dp)
                )
            },
            label = {
                Text(
                    text = AppTitles.LBAccount.key,
                    color = MaterialTheme.colors.background
                )
            },
            enabled = false,
            selected = false,
            modifier = modifier.padding(0.dp, 5.dp), // elevates the navigation item
            onClick = { /*TODO*/ }
        )
    }
}
/*
@Preview(showBackground = true)
@Composable
fun DefaultPreviewOther() {
    val isLogin = true
    val isSignUp = false
    var isSignUpIn: Boolean
    AppTheme(darkTheme = isDark.value) {
        isSignUpIn = isLogin or isSignUp
        MyScaffold(
            contFunc = { CreateAccount(isLogin) },
            bottFunc = { MyBottomBar(isLogin = isLogin, isSignUp = isSignUp) },
            isSignUpIn = isSignUpIn
        )
    }
}
*/