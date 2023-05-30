package xyz.izadi.adibide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import xyz.izadi.adibide.ui.AdibideApp
import xyz.izadi.core.designsystem.theme.AdibideTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AdibideTheme {
                AdibideApp()
            }
        }
    }
}
