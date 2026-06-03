package ar.edu.ort.lendlyapp.ui.screens.loans

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ar.edu.ort.lendlyapp.data.remote.dto.LoanDto
import ar.edu.ort.lendlyapp.ui.components.LoanCard
import ar.edu.ort.lendlyapp.ui.components.PaidLoanRow
import ar.edu.ort.lendlyapp.ui.components.PrimaryButton
import ar.edu.ort.lendlyapp.ui.theme.BackgroundScreen
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary
import ar.edu.ort.lendlyapp.ui.theme.ContentSecondary
import ar.edu.ort.lendlyapp.ui.theme.SentimentNegative

@Composable
fun ActiveLoansScreen(
    viewModel: LoanViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.loansState.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadLoans() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundScreen)
    ) {
        // Top bar: back + calendario.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = ContentPrimary
                )
            }
            Spacer(Modifier.weight(1f))
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Outlined.CalendarToday,
                    contentDescription = "Calendar",
                    tint = ContentPrimary
                )
            }
        }

        Text(
            text = "Active loans",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = ContentPrimary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        when (val s = state) {
            is LoansUiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is LoansUiState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = s.message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = SentimentNegative
                    )
                    Spacer(Modifier.height(16.dp))
                    PrimaryButton(text = "Reintentar", onClick = { viewModel.loadLoans() })
                }
            }

            is LoansUiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (s.present.isNotEmpty()) {
                        SectionHeader("Present")
                        s.present.forEach { loan ->
                            LoanCard(
                                lender = loan.lender,
                                lenderLogo = loan.lenderLogo,
                                amount = loan.amountDue ?: loan.amount,
                                nextPaymentLabel = loan.nextPaymentLabel
                            )
                        }
                    }

                    if (s.recent.isNotEmpty()) {
                        Spacer(Modifier.height(4.dp))
                        SectionHeader("Recent Loans")
                        s.recent.forEach { loan ->
                            PaidLoanRow(
                                date = loan.endDate ?: loan.startDate ?: "\u2014",
                                title = loan.purpose ?: loan.lender,
                                lender = loan.lender
                            )
                        }
                    }

                    if (s.present.isEmpty() && s.recent.isEmpty()) {
                        Text(
                            text = "Todavia no tenes prestamos.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = ContentSecondary,
                            modifier = Modifier.padding(top = 24.dp)
                        )
                    }

                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = ContentPrimary,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}