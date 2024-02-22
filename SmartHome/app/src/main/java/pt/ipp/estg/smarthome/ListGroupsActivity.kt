package pt.ipp.estg.smarthome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.ipp.estg.smarthome.ui.theme.AppTheme
import pt.ipp.estg.smarthome.common.AppTitles

class ListGroupsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent() {

        }
    }
}

@Composable
fun listGroups() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,

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
            text = AppTitles.LBGroup.key,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        LazyColumn {
            item {
                Button(
                    onClick = {
                        //your onclick code
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                    modifier = Modifier.width(width = 300.dp).padding(10.dp).clip(RoundedCornerShape(20.dp))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outline_bookmark_border_24),
                        tint = MaterialTheme.colors.background,
                        contentDescription = "Room 1",
                        modifier = Modifier.padding(start = 4.dp).size(40.dp)
                    )
                    Text(text = "Room 1", color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth().padding(10.dp).padding(start = 30.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Button(
                    onClick = {
                        //your onclick code
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                    modifier = Modifier.width(width = 300.dp).padding(10.dp).clip(RoundedCornerShape(20.dp))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outline_bookmark_border_24),
                        tint = MaterialTheme.colors.background,
                        contentDescription = "Room 2",
                        modifier = Modifier.padding(start = 4.dp).size(40.dp)
                    )
                    Text(text = "Room 2", color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth().padding(10.dp).padding(start = 30.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Button(
                    onClick = {
                        //your onclick code
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                    modifier = Modifier.width(width = 300.dp).padding(10.dp).clip(RoundedCornerShape(20.dp))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outline_bookmark_border_24),
                        tint = MaterialTheme.colors.background,
                        contentDescription = "Kitchen",
                        modifier = Modifier.padding(start = 4.dp).size(40.dp)
                    )
                    Text(text = "Kitchen", color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth().padding(10.dp).padding(start = 30.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Button(
                    onClick = {
                        //your onclick code
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                    modifier = Modifier.width(width = 300.dp).padding(10.dp).clip(RoundedCornerShape(20.dp))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outline_bookmark_border_24),
                        tint = MaterialTheme.colors.background,
                        contentDescription = "Hall",
                        modifier = Modifier.padding(start = 4.dp).size(40.dp)
                    )
                    Text(text = "Hall", color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth().padding(10.dp).padding(start = 30.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Button(
                    onClick = {
                        //your onclick code
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                    modifier = Modifier.width(width = 300.dp).padding(10.dp).clip(RoundedCornerShape(20.dp))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outline_bookmark_border_24),
                        tint = MaterialTheme.colors.background,
                        contentDescription = "Garden",
                        modifier = Modifier.padding(start = 4.dp).size(40.dp)
                    )
                    Text(text = "Garden", color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth().padding(10.dp).padding(start = 30.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Button(
                    onClick = {
                        //your onclick code
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                    modifier = Modifier.width(width = 300.dp).padding(10.dp).clip(RoundedCornerShape(20.dp))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outline_bookmark_border_24),
                        tint = MaterialTheme.colors.background,
                        contentDescription = "Garage",
                        modifier = Modifier.padding(start = 4.dp).size(40.dp)
                    )
                    Text(text = "Garage", color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth().padding(10.dp).padding(start = 30.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Button(
                    onClick = {
                        //your onclick code
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                    modifier = Modifier.width(width = 300.dp).padding(10.dp).clip(RoundedCornerShape(20.dp))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outline_bookmark_border_24),
                        tint = MaterialTheme.colors.background,
                        contentDescription = "Dinning Room",
                        modifier = Modifier.padding(start = 4.dp).size(40.dp)
                    )
                    Text(text = "Dinning Room", color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth().padding(10.dp).padding(start = 30.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Button(
                    onClick = {
                        //your onclick code
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                    modifier = Modifier.width(width = 300.dp).padding(10.dp).clip(RoundedCornerShape(20.dp))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outline_bookmark_border_24),
                        tint = MaterialTheme.colors.background,
                        contentDescription = "WC",
                        modifier = Modifier.padding(start = 4.dp).size(40.dp)
                    )
                    Text(text = "WC", color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth().padding(10.dp).padding(start = 30.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewListGroup() {
    AppTheme(darkTheme = false) {
        listGroups()
    }
}