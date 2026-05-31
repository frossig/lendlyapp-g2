package ar.edu.ort.lendlyapp.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.ort.lendlyapp.ui.theme.LendlyTheme

/**
 * Home — landing post-login.
 *
 * TODO (compañero): según Figma, debe mostrar Notifications, Calendar, Wallet,
 * Cash In, Preview Pending Loans, Promotional Items. Crear HomeViewModel que
 * pegue a /users/{id} y /loans para mostrar saldo y préstamos pendientes.
 */
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(text = "Home (placeholder)")
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    LendlyTheme { HomeScreen() }
}
