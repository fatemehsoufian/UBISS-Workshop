package org.uni.myapplication

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import org.uni.mobilecomputinghomework1.R
import org.uni.myapplication.ui.theme.MyApplicationTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // Notification permission launcher
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d("Permission", "Notification permission granted")
            runNotificationTests()
        } else {
            Log.d("Permission", "Notification permission denied")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create notification channel first
        NotificationHelper.createNotificationChannel(this)

        // Request notification permission if needed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            runNotificationTests()
        }

        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NotificationTestButton()
                    MainScreen()
                }
            }
        }
    }

    private fun runNotificationTests() {
        // 1. Verify channel
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = manager.getNotificationChannel(NotificationHelper.CHANNEL_ID)
            Log.d("NotificationTest", "Channel exists: ${channel != null}")
            Log.d("NotificationTest", "Channel importance: ${channel?.importance}")
        }

        // 2. Verify icon
        try {
            val resId = resources.getIdentifier("ic_notification", "drawable", packageName)
            Log.d("IconCheck", "Notification icon exists: ${resId != 0}")
        } catch (e: Exception) {
            Log.e("IconCheck", "Error checking icon", e)
        }

        // 3. Check manufacturer-specific restrictions
        checkManufacturerRestrictions()

        // 4. Immediate test notification
        NotificationHelper.sendNotification(
            this,
            "Initial Test",
            "This is an initial test notification"
        )
    }

    private fun checkManufacturerRestrictions() {
        // Xiaomi/Huawei/Oppo special checks
        when (Build.MANUFACTURER.lowercase()) {
            "xiaomi" -> Log.d("DeviceCheck", "Xiaomi device - check MIUI optimizations")
            "huawei" -> Log.d("DeviceCheck", "Huawei device - check battery settings")
            "oppo" -> Log.d("DeviceCheck", "Oppo device - check background restrictions")
        }

        // Check battery optimization
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val powerManager = getSystemService(POWER_SERVICE) as PowerManager
            val isIgnoringOptimizations = powerManager.isIgnoringBatteryOptimizations(packageName)
            Log.d("BatteryCheck", "Ignoring battery optimizations: $isIgnoringOptimizations")
        }
    }
}

@Composable
fun NotificationTestButton() {
    val context = LocalContext.current
    Button(
        onClick = {
            // Comprehensive test
            NotificationHelper.sendNotification(
                context,
                "Manual Test",
                "This is a manual test notification"
            )

            // Alternative method test
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(
                999, // Different ID to avoid collision
                NotificationCompat.Builder(context, NotificationHelper.CHANNEL_ID)
                    .setSmallIcon(R.drawable.sunrise)
                    .setContentTitle("Alternative Method")
                    .setContentText("Testing direct manager.notify()")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build()
            )
        },
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Test Notifications")
    }
}