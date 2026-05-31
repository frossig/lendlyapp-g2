package ar.edu.ort.lendlyapp.ui.screens.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Sección History — Recent Paid Loans + Recent Loans.
 *
 * TODO (compañero): usar /transactions y /loans, filtrar por estado.
 */
@Composable
fun HistoryScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("History (placeholder)")
    }
}
