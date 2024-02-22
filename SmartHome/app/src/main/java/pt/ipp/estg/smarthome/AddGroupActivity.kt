package pt.ipp.estg.smarthome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import pt.ipp.estg.smarthome.common.AppTitles
import pt.ipp.estg.smarthome.ui.theme.AppTheme

class AddGroupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent() {

        }
    }
}

@Composable
fun addGroups() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Text(
            text = AppTitles.LBGroup.key,
            modifier = Modifier
                .padding(16.dp)
                .padding(top = 30.dp, bottom = 80.dp),
            fontSize = 30.sp,
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.icons8_smart_home_96),
                contentDescription = "AddGroup Icon",
                modifier = Modifier.size(100.dp),
            )
        }
        Text(
            text = AppTitles.AppTitle.key,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "No groups yet. Please add a new one.",
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colors.primary
        )
        Button(
            onClick = {
                //your onclick code
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            modifier = Modifier.width(width = 300.dp).padding(10.dp).clip(RoundedCornerShape(20.dp))
        ) {
            Text(text = "Create Group", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewAddGroup() {
    AppTheme(darkTheme = false) {
        addGroups()
    }
}