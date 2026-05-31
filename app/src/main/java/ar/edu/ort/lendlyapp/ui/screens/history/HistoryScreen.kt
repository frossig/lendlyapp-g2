package ar.edu.ort.lendlyapp.ui.screens.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// TODO (compañero): Recent Paid Loans + Recent Loans; GET /transactions + GET /loans (filtrar por status).
@Composable
fun HistoryScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("History (placeholder)")
    }
}
