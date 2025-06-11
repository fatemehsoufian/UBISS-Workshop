package org.uni.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.delay
import org.uni.myapplication.ui.theme.MyApplicationTheme
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun MainScreen() {
    Scaffold(topBar = { MyTopAppBar() }) {
        Column(modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(it)) {
            SunlightProgressBar(0.3f)
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
                    modifier = Modifier.padding(12.dp),
                    text = "Hey Sara :)",
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text= currentTime.format(timeFormatter),
                    color = Color(0xFFE0E0E0)
                )
            }

        },

    )
}

@Composable
fun SunlightProgressBar(progress: Float) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
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