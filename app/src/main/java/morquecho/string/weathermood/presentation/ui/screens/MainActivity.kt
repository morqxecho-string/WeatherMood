package morquecho.string.weathermood.presentation.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import dagger.hilt.android.AndroidEntryPoint
import morquecho.string.weathermood.domain.Weather
import morquecho.string.weathermood.domain.WeatherCity
import morquecho.string.weathermood.presentation.ui.theme.WeatherMoodTheme
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //private val weatherViewModel: WeatherViewModel by viewModels()
    /*val weatherViewModel: WeatherViewModel by viewModels()*/

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            WeatherMoodTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("WeatherMood") }
                        )
                    }
                ) { innerPadding ->
                    WeatherMood(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun WeatherMood(modifier: Modifier, weatherViewModel: WeatherViewModel = hiltViewModel()) {
    Column(modifier = modifier
                .fillMaxSize()
    ) {
        SearchCityField(modifier = modifier)

        /*Spacer(modifier = Modifier)*/
        CityWeather(
            modifier,
            weatherViewModel
        )
    }
}

@Composable
fun CityWeather(modifier: Modifier = Modifier, weatherViewModel: WeatherViewModel) {
    val weatherState by weatherViewModel.state.collectAsStateWithLifecycle()
    /*val weatherState = WeatherCity(
        id = 1,
        name = "Sample City",
        weather = listOf(
            Weather(
                id = 800,
                main = "Clear",
                description = "clear sky",
                icon = "01d"
            )
        ),
        temp = 25.0,
        feelsLike = 26.0,
        tempMin = 20.0,
        tempMax = 30.0,
        humidity = 50,
        windSpeed = 5.0,
        windDeg = 180,
        sunrise = 1672531200,
        sunset = 1672574400,
        country = "US"
    )*/

    if (weatherState == null) {
        Text(
            text = "Search a city :)",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 25.dp),
            color = MaterialTheme.colorScheme.onSurface
        )
    } else {
        val weather: Weather = weatherState!!.weather[0];

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = modifier,
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.DarkGray
                )
            ) {
                //City
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    text = weatherState!!.name,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    //icon
                    WeatherIcon(weather.icon)

                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.Center
                    ) {
                        //weather
                        Text(
                            text = "${weatherState!!.temp.roundToInt()}°",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 50.sp
                        )

                        Text(
                            text = "Feels like ${weatherState!!.feelsLike.roundToInt()}°",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 20.sp
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            //min
                            Text(
                                modifier = Modifier
                                    .padding(0.dp, 0.dp, 10.dp, 0.dp),
                                text = "Min ${weatherState!!.tempMin.roundToInt()}°",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 18.sp
                            )

                            //max
                            Text(
                                text = "Max ${weatherState!!.tempMax.roundToInt()}°",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherIcon(iconCode: String) {
    val iconUrl = "https://openweathermap.org/img/wn/${iconCode}@2x.png"

    AsyncImage(
        model = iconUrl,
        contentDescription = "Weather icon",
        modifier = Modifier.size(150.dp)
    )
}

@Composable
fun SearchCityField(modifier: Modifier = Modifier, weatherViewModel: WeatherViewModel = hiltViewModel()) {
    var cityToSearch by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

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
            weatherViewModel.loadWeatherCity(cityToSearch)
            keyboardController?.hide()
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

@Preview(showBackground = true, name = "Weather Screen Preview")
@Composable
fun WeatherScreenPreview() {
    /*val mockWeather = WeatherCity(
        id = 1,
        name = "Sample City",
        weather = listOf(
            Weather(
                id = 800,
                main = "Clear",
                description = "clear sky",
                icon = "01d"
            )
        ),
        temp = 25.0,
        feelsLike = 26.0,
        tempMin = 20.0,
        tempMax = 30.0,
        humidity = 50,
        windSpeed = 5.0,
        windDeg = 180,
        sunrise = 1672531200,
        sunset = 1672574400,
        country = "US"
    )

    val mockViewModel = remember { MockWeatherViewModel(mockWeather) }

    WeatherMoodTheme {
        WeatherMood(modifier = Modifier, mockViewModel)
    }*/
}