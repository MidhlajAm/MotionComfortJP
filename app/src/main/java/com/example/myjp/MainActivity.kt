package com.example.myjp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.myjp.ui.navigation.AppNavRoot
import com.example.myjp.ui.theme.MyJPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var darkModeEnabled by rememberSaveable { mutableStateOf(false) }

            MyJPTheme(darkTheme = darkModeEnabled, dynamicColor = false) {
                AppNavRoot(
                    darkModeEnabled = darkModeEnabled,
                    onDarkModeChange = { darkModeEnabled = it }
                )
            }
        }
    }
}
