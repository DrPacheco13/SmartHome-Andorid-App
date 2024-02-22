package pt.ipp.estg.smarthome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.ipp.estg.smarthome.ui.theme.AppTheme

class ListDevicesGroupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent() {

        }
    }
}

@Composable
fun listDevicesGroup() {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.icons8_smart_home_96),
                contentDescription = "AddGroup Icon",
                modifier = Modifier.size(60.dp)
            )
        }
        Text(
            text = "Groups: Room1",
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(20.dp))
                        .background(color = MaterialTheme.colors.surface)
                        .size(width = 350.dp, height = 100.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.size(width = 330.dp, height = 100.dp)
                            .offset(10.dp, 0.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_outline_lightbulb_24),
                            tint = MaterialTheme.colors.secondary,
                            contentDescription = "Add Device",
                            modifier = Modifier.size(60.dp).clickable { }
                        )
                        Text("Lamp 1", fontSize = 30.sp, color = MaterialTheme.colors.primary)
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_power_settings_new_24),
                            tint = MaterialTheme.colors.background,
                            contentDescription = "Add Device",
                            modifier = Modifier.size(60.dp).clickable { }
                        )
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(20.dp))
                        .background(color = MaterialTheme.colors.surface)
                        .size(width = 350.dp, height = 100.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.size(width = 330.dp, height = 100.dp)
                            .offset(10.dp, 0.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_outline_lightbulb_24),
                            tint = MaterialTheme.colors.secondary,
                            contentDescription = "Add Device",
                            modifier = Modifier.size(60.dp).clickable { }
                        )
                        Text("Lamp 2", fontSize = 30.sp, color = MaterialTheme.colors.primary)
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_power_settings_new_24),
                            tint = Color.Red,
                            contentDescription = "Add Device",
                            modifier = Modifier.size(60.dp).clickable { }
                        )
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(20.dp))
                        .background(color = MaterialTheme.colors.surface)
                        .size(width = 350.dp, height = 100.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.size(width = 330.dp, height = 100.dp)
                            .offset(10.dp, 0.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_outline_lightbulb_24),
                            tint = MaterialTheme.colors.secondary,
                            contentDescription = "Add Device",
                            modifier = Modifier.size(60.dp).clickable { }
                        )
                        Text("Lamp 3", fontSize = 30.sp, color = MaterialTheme.colors.primary)
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_power_settings_new_24),
                            tint = MaterialTheme.colors.background,
                            contentDescription = "Add Device",
                            modifier = Modifier.size(60.dp).clickable { }
                        )
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(20.dp))
                        .background(color = MaterialTheme.colors.surface)
                        .size(width = 350.dp, height = 100.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.size(width = 330.dp, height = 100.dp)
                            .offset(10.dp, 0.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_outline_settings_input_svideo_24),
                            tint = MaterialTheme.colors.secondary,
                            contentDescription = "Add Device",
                            modifier = Modifier.size(60.dp).clickable { }
                        )
                        Text("Plug 1", fontSize = 30.sp, color = MaterialTheme.colors.primary)
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_power_settings_new_24),
                            tint = MaterialTheme.colors.background,
                            contentDescription = "Add Device",
                            modifier = Modifier.size(60.dp).clickable { }
                        )
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(20.dp))
                        .background(color = MaterialTheme.colors.surface)
                        .size(width = 350.dp, height = 100.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.size(width = 330.dp, height = 100.dp)
                            .offset(10.dp, 0.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_blinds_24),
                            tint = MaterialTheme.colors.secondary,
                            contentDescription = "Add Device",
                            modifier = Modifier.size(60.dp).clickable { }
                        )
                        Text("Blinds 1", fontSize = 30.sp, color = MaterialTheme.colors.primary)
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_power_settings_new_24),
                            tint = MaterialTheme.colors.background,
                            contentDescription = "Add Device",
                            modifier = Modifier.size(60.dp).clickable { }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewListDevicesGroup() {
    AppTheme(darkTheme = false) {
        listDevicesGroup()
    }
}