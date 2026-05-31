package ar.edu.ort.lendlyapp.ui.screens.loans

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// TODO (compañero): NavHost interno con How to Loan / Apply Loan / Active Loan; GET /loans + POST /loans/apply.
@Composable
fun LoansScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Loans (placeholder)")
    }
}
