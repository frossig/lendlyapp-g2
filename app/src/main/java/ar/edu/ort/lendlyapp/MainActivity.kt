package ar.edu.ort.lendlyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ar.edu.ort.lendlyapp.ui.navigation.AppNavigation
import ar.edu.ort.lendlyapp.ui.theme.LendlyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LendlyTheme {
                AppNavigation()
            }
        }   
    }
}
