package org.uni.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import org.uni.mobilecomputinghomework1.R
import org.uni.myapplication.ui.theme.MyApplicationTheme
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun MainScreen(viewModel: SunlightViewModel = hiltViewModel()) {
    val sunlightInfo by viewModel.sunlightInfo.collectAsState()
    val weatherIcon = when (sunlightInfo?.weatherDescription) {
        "Cloudy" -> R.drawable.cloudy
        "Sunny" -> R.drawable.sunny
        "Rain" -> R.drawable.rainy
        else -> R.drawable.cloudy
    }
    Scaffold(topBar = { MyTopAppBar() }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .padding(paddingValues)
        ) {
            // Sunlight Progress or Loading
            sunlightInfo?.let { info ->
                SunlightProgressBar(info)
            } ?: run {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            // Cards row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Weather Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .height(150.dp), // FIXED height
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = weatherIcon),
                            modifier = Modifier.size(60.dp),
                            contentDescription = "My Icon",
                            tint = Color.Unspecified // Or apply a color
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = sunlightInfo?.weatherDescription ?: "Cloudy",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "${sunlightInfo?.temperature ?: 22}°C",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                // Sunlight Exposure Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .height(150.dp), // FIXED height
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = if (true) Icons.Default.Search else Icons.Default.Clear,
                            contentDescription = "Sunlight Status",
                            modifier = Modifier.size(48.dp),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (true) "Got sunlight ☀️" else "No sunlight today",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            // Divider and section title
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = "Benefits of Sunlight",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 24.sp,
                style = MaterialTheme.typography.titleMedium,
            )

            // Lazy column with benefits
            val quoteList = StaticInfoData.InfoList
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                items(quoteList) { quote ->
                    InfoItem(quote)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "✨ Shine Shine ✨",
//                    fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 20.sp,
                style = MaterialTheme.typography.titleMedium,
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFFFFF176), // Replace with your custom color 'X'
            titleContentColor = Color.Black
        )
    )
}

@Composable
fun SunlightProgressBar(sunLightModel: SunLightModel) {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val sunriseTime = LocalTime.parse(sunLightModel.sunrise, timeFormatter)
    val sunsetTime = LocalTime.parse(sunLightModel.sunset, timeFormatter)

    // Get current date and time
    val currentDateTime = LocalDateTime.now()
    val currentTime = currentDateTime.toLocalTime()

    // Calculate today's sunrise and sunset as LocalDateTime
    val todaySunrise = currentDateTime.with(sunriseTime)
    val todaySunset = if (sunsetTime.isBefore(sunriseTime)) {
        // Handle case where sunset is technically tomorrow (unlikely for sunrise/sunset)
        currentDateTime.plusDays(1).with(sunsetTime)
    } else {
        currentDateTime.with(sunsetTime)
    }

    // Total duration between sunrise and sunset
    val totalDuration = Duration.between(todaySunrise, todaySunset).toMinutes().toFloat()

    // Current progress calculation
    val progress = when {
        currentDateTime.isBefore(todaySunrise) -> 0f // Before sunrise
        currentDateTime.isAfter(todaySunset) -> 1f // After sunset
        else -> {
            val elapsed = Duration.between(todaySunrise, currentDateTime).toMinutes().toFloat()
            (elapsed / totalDuration).coerceIn(0f, 1f)
        }
    }

    // Time left calculation
    val timeLeft = when {
        currentDateTime.isBefore(todaySunrise) -> Duration.between(currentDateTime, todaySunset)
        currentDateTime.isAfter(todaySunset) -> Duration.ZERO
        else -> Duration.between(currentDateTime, todaySunset)
    }

    val hoursLeft = timeLeft.toHours()
    val minutesLeft = timeLeft.toMinutes() % 60

    var isHovered by remember { mutableStateOf(false) }

    // Auto-refresh every minute
    val currentMinute by rememberUpdatedState(currentDateTime.minute)
    LaunchedEffect(currentMinute) {
        delay(60_000) // Refresh every minute
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "🌅 Sunrise: ${sunLightModel.sunrise}", fontSize = 14.sp)
            Text(text = "🌇 Sunset: ${sunLightModel.sunset}", fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp) // Bigger progress bar
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = { isHovered = true },
                        onPress = { isHovered = false }
                    )
                }
        ) {
            // Background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            )

            // Progress
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .fillMaxHeight()
                    .background(
                        color = getVividSunProgressColor(progress), // Green to red gradient
                        shape = RoundedCornerShape(12.dp)
                    )
            )

            if (isHovered) {
                Popup(
                    alignment = Alignment.TopCenter,
                    offset = IntOffset(0, -50),
                    properties = PopupProperties(focusable = false)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = when {
                                currentDateTime.isBefore(todaySunrise) ->
                                    "Sunrise in ${todaySunrise.toLocalTime().format(timeFormatter)}"

                                currentDateTime.isAfter(todaySunset) ->
                                    "Sunset was at ${
                                        todaySunset.toLocalTime().format(timeFormatter)
                                    }"

                                else -> "$hoursLeft hr $minutesLeft min left"
                            },
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

private fun getVividSunProgressColor(progress: Float): Color {
    // Bright green (lime) to bright red
    val brightGreen = Color(0xFF00FF00)  // Vibrant green
    val brightRed = Color(0xFFFF0000)    // Vibrant red

    // Blend colors based on progress
    return when {
        progress < 0.5f -> {
            // Transition from green to yellow in first half
            val adjustedProgress = progress * 2
            Color(
                red = brightGreen.red + (Color.Yellow.red - brightGreen.red) * adjustedProgress,
                green = brightGreen.green + (Color.Yellow.green - brightGreen.green) * adjustedProgress,
                blue = brightGreen.blue + (Color.Yellow.blue - brightGreen.blue) * adjustedProgress
            )
        }

        else -> {
            // Transition from yellow to red in second half
            val adjustedProgress = (progress - 0.5f) * 2
            Color(
                red = Color.Yellow.red + (brightRed.red - Color.Yellow.red) * adjustedProgress,
                green = Color.Yellow.green + (brightRed.green - Color.Yellow.green) * adjustedProgress,
                blue = Color.Yellow.blue + (brightRed.blue - Color.Yellow.blue) * adjustedProgress
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressBarPreview() {
    MyApplicationTheme {
        SunlightProgressBar(
            sunLightModel = SunLightModel(
                sunset = "19:01",
                sunrise = "06:10",
                cloudiness = 1,
                weatherDescription = "",
                temperature = 12.0f,
                humidity = 1
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MyApplicationTheme {
        MainScreen()
    }
}