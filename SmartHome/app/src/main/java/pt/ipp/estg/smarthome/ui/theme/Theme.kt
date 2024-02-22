package pt.ipp.estg.smarthome.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkThemeColors = darkColors(
    primary = Teal300, // text color
    primaryVariant = Teal400, // icon text color
    secondary = Teal650, // bottom bar & button & top bar icon color
    background = Teal800, // background & bottom icons color
    surface = Teal700, // opposite theme background color
)

private val LightThemeColors = lightColors(
    primary = Blue300, // text color
    primaryVariant = Blue400, // icon text color
    secondary = Blue650, // bottom bar box & buttons & top bar icon color
    background = Blue800, // background & bottom icons color
    surface = Blue700, // opposite theme background color
)

@Composable
fun AppTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if(darkTheme) DarkThemeColors else LightThemeColors
    ){
        content()
    }
}