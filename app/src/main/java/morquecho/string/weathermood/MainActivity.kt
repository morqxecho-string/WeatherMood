package morquecho.string.weathermood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import morquecho.string.weathermood.ui.theme.WeatherMoodTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherMoodTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("WeatherMood") }
                        )
                    }
                ) { innerPadding ->
                    SearchCity(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun SearchCity(modifier: Modifier = Modifier) {
    var cityToSearch by remember { mutableStateOf("") }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(15.dp)),
        value = cityToSearch,
        onValueChange = { text: String ->
            cityToSearch = text
        },
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        ),
        placeholder = @Composable { Text("Ciudad...") },
        leadingIcon = @Composable { Icon(Icons.Default.Search, contentDescription = "Buscar") },
        trailingIcon = @Composable { IconButton(onClick = { cityToSearch = "" }) { Icon(Icons.Default.Clear, contentDescription = "Borrar") } },
        isError = false,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            capitalization = KeyboardCapitalization.Words
        ),
        keyboardActions = KeyboardActions(onSearch = {

        }),
        singleLine = true,
        shape = RoundedCornerShape(size = 8.dp),
        colors = TextFieldDefaults.colors(
            focusedLabelColor = Color.Red,
            cursorColor = Color.Red,
            errorIndicatorColor = Color.Red
        )
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherMoodTheme {
        SearchCity()
    }
}