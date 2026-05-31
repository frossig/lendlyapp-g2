package ar.edu.ort.lendlyapp.ui.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// TODO (compañero): replicar diseño Figma (imagen, tipografía, CTAs).
@Composable
fun OnboardingScreen(
    onGoToLogin: () -> Unit,
    onGoToRegister: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("LendlyApp")
        Button(onClick = onGoToLogin) { Text("Log in") }
        OutlinedButton(onClick = onGoToRegister) { Text("Sign up") }
    }
}
