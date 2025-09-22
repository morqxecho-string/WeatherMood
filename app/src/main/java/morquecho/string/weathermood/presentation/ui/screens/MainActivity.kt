package morquecho.string.weathermood.presentation.ui.screens

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import morquecho.string.weathermood.R
import morquecho.string.weathermood.domain.Weather
import morquecho.string.weathermood.domain.WeatherUIState
import morquecho.string.weathermood.presentation.ui.theme.WeatherMoodTheme
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val splashScreen = installSplashScreen()

        var keepSplashOnScreen = true
        lifecycleScope.launch {
            delay(3000)
            keepSplashOnScreen = false
        }

        splashScreen.setKeepOnScreenCondition { keepSplashOnScreen }

        setContent {
            WeatherMoodTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    WeatherMood(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Idea: Adds top five or seven most importante US cities on Chips, and the user can click on them and search that city
// Idea: Adds feature where the user, when focusing on the search box, displays a list of the most important US cities
// Idea: Adds feature where the user, when focusing on the search box, displays a list of the last ten cities searched
// Idea: Adds refresh button on the TopBar, where the user can make a click and refresh the current city
@Composable
fun WeatherMood(modifier: Modifier, weatherViewModel: WeatherViewModel = hiltViewModel()) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TemperatureScaleDropDownMenu(weatherViewModel)
        SearchCityField()
        WeatherMood(weatherViewModel)
    }
}

// Idea: Adds this Temperature Scale DropDownMenu on the TopBar
@Composable
fun TemperatureScaleDropDownMenu(weatherViewModel: WeatherViewModel) {
    val unitState by weatherViewModel.stateUnit.collectAsStateWithLifecycle()
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Fahrenheit", "Celsius", "Kelvin")

    Row(
        modifier = Modifier
            .padding(20.dp, 5.dp, 20.dp, 5.dp)
            .wrapContentSize(Alignment.TopStart)
    ) {
        Text(
            text = stringResource(R.string.temperature_unit),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(0.dp, 12.dp, 20.dp, 0.dp),
            color = MaterialTheme.colorScheme.onSurface
        )

        Box {
            Button(
                onClick = { expanded = true },
                modifier = Modifier
            ) {
                Text(unitSelectionConversionInverted(unitState))
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            expanded = false
                            val unitSelected = unitSelectionConversion(item)
                            weatherViewModel.setUnit(unitSelected)
                        }
                    )
                }
            }
        }
    }
}

fun unitSelectionConversion(selectedText: String): String {
    return when (selectedText) {
        "Fahrenheit" -> "imperial"
        "Celsius" -> "metric"
        "Kelvin" -> "standard"
        else -> "standard"
    }
}

fun unitSelectionConversionInverted(selectedText: String): String {
    return when (selectedText) {
        "imperial" -> "Fahrenheit"
        "metric" -> "Celsius"
        "standard" -> "Kelvin"
        else -> "standard"
    }
}

//region Test
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
//endregion

@Composable
fun WeatherMood(weatherViewModel: WeatherViewModel) {
    val uiWeatherState: WeatherUIState by weatherViewModel.uiWeatherState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = uiWeatherState,
            transitionSpec = {
                (fadeIn() + expandVertically()).togetherWith(fadeOut() + shrinkHorizontally())
            }
        ) { targetState ->
            when (targetState) {
                is WeatherUIState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(50.dp)
                    )
                }
                is WeatherUIState.Success -> {
                    WeatherCity(targetState)
                }
                is WeatherUIState.Error -> {
                    WeatherMoodError(targetState)
                }
                is WeatherUIState.Initial -> {
                    WeatherMoodSearchCity()
                }
            }
        }
    }
}

@Composable
fun WeatherMoodSearchCity() {
    Text(
        text = stringResource(R.string.search_a_city),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 5.dp, 20.dp, 5.dp),
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun WeatherMoodError(uiWeatherState: WeatherUIState) {
    val error = (uiWeatherState as WeatherUIState.Error).message

    Text(
        text = stringResource(R.string.something_went_wrong_try_again, error),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 5.dp, 20.dp, 5.dp),
        color = MaterialTheme.colorScheme.onSurface
    )
}

// Idea: Adds a share button where the user can share, in text, to different apps, the weather info
@Composable
fun WeatherCity(uiWeatherState: WeatherUIState) {
    val weatherState = (uiWeatherState as WeatherUIState.Success).data

    val weather: Weather = weatherState.weather[0]

    Column(
        modifier = Modifier
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier,
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
                text = "${weatherState.name}, ${weatherState.country}",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    //icon
                    WeatherIcon(weather.icon, weather.description)

                    Text(
                        modifier = Modifier
                            .padding(20.dp),
                        text = weather.description.replaceFirstChar { it.titlecase() },
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    //weather
                    Text(
                        text = "${weatherState.temp.roundToInt()}°",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 50.sp
                    )

                    Text(
                        text = stringResource(
                            R.string.feels_like,
                            weatherState.feelsLike.roundToInt()
                        ),
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
                            text = stringResource(
                                R.string.min_temperature,
                                weatherState.tempMin.roundToInt()
                            ),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 18.sp
                        )

                        //max
                        Text(
                            text = stringResource(
                                R.string.max_temperature,
                                weatherState.tempMax.roundToInt()
                            ),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherIcon(iconCode: String, weatherDescription: String) {
    val iconUrl = "https://openweathermap.org/img/wn/${iconCode}@2x.png"

    AsyncImage(
        model = iconUrl,
        contentDescription = weatherDescription,
        modifier = Modifier.size(150.dp)
    )
}

// Adds the error message if the user sends empty data to the error options of TextField
// The trailingIcon, clear icon, display only if there is written text
@Composable
fun SearchCityField(weatherViewModel: WeatherViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var cityToSearch by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        modifier = Modifier
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
        placeholder = @Composable { Text(stringResource(R.string.search_an_us_city)) },
        leadingIcon = @Composable { Icon(Icons.Default.Search, contentDescription = stringResource(R.string.search_city)) },
        trailingIcon = @Composable { IconButton(onClick = { cityToSearch = "" }) { Icon(Icons.Default.Clear, contentDescription = stringResource(
            R.string.delete_text
        )) } },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            capitalization = KeyboardCapitalization.Words
        ),
        keyboardActions = KeyboardActions(onSearch = {
            if (cityToSearch.isEmpty()) {
                Toast.makeText(context, context.getString(R.string.you_must_write_a_city_in_the_text_field), Toast.LENGTH_LONG).show()
                return@KeyboardActions
            }

            weatherViewModel.loadWeatherCity(cityToSearch)
            keyboardController?.hide()
        }),
        singleLine = true,
        shape = RoundedCornerShape(size = 8.dp),
        colors = TextFieldDefaults.colors(
            cursorColor = Color.White,
        )
    )
}

// Failed attempt of implement Preview Composable with ViewModel :( a lot of waste time, there are priorities
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
    )*/

    /*val mockViewModel = remember { MockWeatherViewModel(mockWeather) }*/

    /*WeatherMoodTheme {
        *//*WeatherMood(modifier = Modifier, mockViewModel)*//*
        CityWeather()
    }*/
}