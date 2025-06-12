package org.uni.myapplication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import org.uni.myapplication.ui.theme.MyApplicationTheme
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun MainScreen(viewModel: SunlightViewModel = hiltViewModel()) {
    val sunlightInfo by viewModel.sunlightInfo.collectAsState()

    Scaffold(topBar = { MyTopAppBar() }) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(it)
        ) {
            sunlightInfo?.let {info->
                SunlightProgressBar(info)
            } ?: run {
                CircularProgressIndicator()
            }
            Text(
                modifier = Modifier.padding(12.dp),
                text = "Benefits of Sunlight",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 24.sp,
                style = MaterialTheme.typography.titleMedium,
            )
            val quoteList = StaticInfoData.InfoList
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
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
    val currentTime = LocalTime.now()

    val totalMinutes =
        sunriseTime.until(sunsetTime, java.time.temporal.ChronoUnit.MINUTES).toFloat()
    val elapsedMinutes =
        sunriseTime.until(currentTime, java.time.temporal.ChronoUnit.MINUTES).toFloat()
    val progress = (elapsedMinutes / totalMinutes).coerceIn(0f, 1f)
    val progressColor = getGradientColor(progress)

    val minutesLeft =
        currentTime.until(sunsetTime, java.time.temporal.ChronoUnit.MINUTES).coerceAtLeast(0)
    val hoursLeft = minutesLeft / 60
    val remainingMinutes = minutesLeft % 60

    var isHovered by remember { mutableStateOf(false) }

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

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            isHovered = true
                        },
                        onPress = {
                            isHovered = false
                        }
                    )
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRect(color = Color.LightGray.copy(alpha = 0.3f))
                drawRect(
                    color = progressColor,
                    size = androidx.compose.ui.geometry.Size(size.width * progress, size.height)
                )
            }

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
                            text = "$hoursLeft hr $remainingMinutes min left",
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                    }
                }

            }
        }

    }
}


fun getGradientColor(progress: Float): Color {
    val green = Color(0xFF4CAF50)  // Start
    val red = Color(0xFFF44336)    // End

    val r = lerp(green.red, red.red, progress)
    val g = lerp(green.green, red.green, progress)
    val b = lerp(green.blue, red.blue, progress)

    return Color(r, g, b)
}

fun lerp(start: Float, stop: Float, amount: Float): Float {
    return start + (stop - start) * amount
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MyApplicationTheme {
        MainScreen()
    }
}