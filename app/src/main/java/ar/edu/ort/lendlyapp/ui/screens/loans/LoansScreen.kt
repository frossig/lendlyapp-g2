package ar.edu.ort.lendlyapp.ui.screens.loans

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Sección Loans del spec — sub-pantallas: How to Loan, Apply Loan, Active Loan.
 *
 * TODO (compañero): convertir en NavHost interno con esas 3 sub-rutas y crear
 * LoansViewModel que use ApiService.getLoans() y ApiService.applyLoan(...).
 */
@Composable
fun LoansScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Loans (placeholder)")
    }
}
