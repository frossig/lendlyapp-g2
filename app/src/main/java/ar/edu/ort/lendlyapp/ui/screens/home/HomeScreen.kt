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

// TODO (compañero): Notifications, Calendar, Wallet, Cash In, pending loans, promos (ver Figma + GET /users/{id} + GET /loans).
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
