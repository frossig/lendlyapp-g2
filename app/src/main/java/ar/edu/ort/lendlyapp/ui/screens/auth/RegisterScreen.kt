package ar.edu.ort.lendlyapp.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Registro contra POST /auth/create + verificación SMS (mockear el código).
 *
 * TODO (compañero): form multi-step (datos personales → SMS code → success),
 * llamar al RegisterViewModel. Replicar diseño Figma.
 */
@Composable
fun RegisterScreen(
    onRegistered: () -> Unit,
    onGoToLogin: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Register (placeholder)")
        Button(onClick = onRegistered) { Text("Simular alta") }
        TextButton(onClick = onGoToLogin) { Text("Ya tengo cuenta") }
        TextButton(onClick = onBack) { Text("Volver") }
    }
}
