package com.imposter.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.imposter.app.ui.ImposterApp
import com.imposter.app.ui.theme.ImposterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Global crash handler — show error instead of silently closing, and persist
        // the stack trace so it can be shown on the next launch (see android-app-helper.md).
        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            runOnUiThread {
                try {
                    val msg = "${throwable.javaClass.simpleName}: ${throwable.message}\n" +
                        throwable.stackTrace.take(3).joinToString("\n") { "  at $it" }
                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                    getSharedPreferences("crash_log", MODE_PRIVATE)
                        .edit().putString("last_crash", msg).apply()
                } catch (_: Exception) {
                }
            }
            Thread.sleep(3000)
            android.os.Process.killProcess(android.os.Process.myPid())
        }

        val prefs = getSharedPreferences("crash_log", MODE_PRIVATE)
        val lastCrash = prefs.getString("last_crash", null)

        setContent {
            ImposterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    ImposterApp(
                        lastCrash = lastCrash,
                        onClearCrash = { prefs.edit().remove("last_crash").apply() },
                    )
                }
            }
        }
    }
}
