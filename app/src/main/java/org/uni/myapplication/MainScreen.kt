package org.uni.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
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
            sunlightInfo?.let {
                SunlightProgressBar(0.2f, it)
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
    var currentTime by remember { mutableStateOf(LocalTime.now().withSecond(0)) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = LocalTime.now().withSecond(0)
            delay(60_000L) // update once every minute
        }
    }

    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    CenterAlignedTopAppBar(
        title = {
            Column {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Hey Sara :)",
//                    fontWeight = FontWeight.Bold,
//                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = currentTime.format(timeFormatter),
                    color = Color(0xFFE0E0E0)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF00C1E8), // Replace with your custom color 'X'
            titleContentColor = Color.White
        )
    )
}

@Composable
fun SunlightProgressBar(progress: Float, sunLightModel: SunLightModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Row for sunrise and sunset labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "🌅 Sunrise: ${sunLightModel.sunrise}", fontSize = 14.sp)
            Text(text = "🌇 Sunset: ${sunLightModel.sunset}", fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Progress Bar
        LinearProgressIndicator(
            progress = progress.coerceIn(0f, 1f),
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
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