package ar.edu.ort.lendlyapp.ui.screens.loans

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ar.edu.ort.lendlyapp.data.remote.dto.AppliedLoanDto
import ar.edu.ort.lendlyapp.ui.components.PrimaryButton
import ar.edu.ort.lendlyapp.ui.components.formatPhp
import ar.edu.ort.lendlyapp.ui.theme.BackgroundScreen
import ar.edu.ort.lendlyapp.ui.theme.BorderNeutral
import ar.edu.ort.lendlyapp.ui.theme.ContentLink
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary
import ar.edu.ort.lendlyapp.ui.theme.ContentSecondary
import ar.edu.ort.lendlyapp.ui.theme.InteractiveAccent
import ar.edu.ort.lendlyapp.ui.theme.InteractivePrimary
import java.text.NumberFormat
import java.util.Locale

private val phpFormatter: NumberFormat = NumberFormat.getNumberInstance(Locale.US).apply {
    minimumFractionDigits = 2
    maximumFractionDigits = 2
}

private fun formatPhpSuffix(amount: Double): String = "${phpFormatter.format(amount)} PHP"

@Composable
fun LoanSuccessScreen(
    viewModel: LoanViewModel,
    onDone: () -> Unit
) {
    val applyState by viewModel.applyState.collectAsState()
    val loan = (applyState as? ApplyLoanUiState.Success)?.loan

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundScreen)
    ) {
        // Top bar con X.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(BorderNeutral),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onDone) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "Close",
                        tint = ContentPrimary
                    )
                }
            }
            Spacer(Modifier.weight(1f))
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(InteractiveAccent),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = null,
                    tint = InteractivePrimary,
                    modifier = Modifier.size(36.dp)
                )
            }

            Text(
                text = "Added to your account",
                style = MaterialTheme.typography.bodyMedium,
                color = ContentSecondary
            )
            Text(
                text = formatPhpSuffix(loan?.amount ?: 0.0),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = ContentPrimary
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, BorderNeutral, RoundedCornerShape(20.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Loan Amount",
                    style = MaterialTheme.typography.bodyMedium,
                    color = ContentPrimary
                )
            }

            Spacer(Modifier.height(8.dp))

            TransactionDetails(loan = loan)

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Need help?",
                style = MaterialTheme.typography.bodySmall,
                color = ContentSecondary
            )
            Text(
                text = "Go to Help Center",
                style = MaterialTheme.typography.titleMedium,
                color = ContentLink
            )

            Spacer(Modifier.height(8.dp))
        }

        Column(modifier = Modifier.padding(16.dp)) {
            PrimaryButton(text = "Done", onClick = onDone)
        }
    }
}

@Composable
private fun TransactionDetails(loan: AppliedLoanDto?) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Transaction Details",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = ContentPrimary
        )
        DetailRow("Monthly Fee", loan?.installmentAmount?.let { formatPhp(it) } ?: "\u2014")
        DetailRow("Interest", loan?.interestRate?.let { "$it%" } ?: "\u2014")
        DetailRow("Installment plan", loan?.installmentPlan ?: "\u2014")
        DetailRow("Date & Time", loan?.startDate ?: "\u2014")
        DetailRow(
            label = "Transaction Number",
            value = loan?.id?.let { "#$it" } ?: "\u2014",
            link = true
        )
    }
}

@Composable
private fun DetailRow(label: String, value: String, link: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = ContentSecondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = if (link) ContentLink else ContentPrimary,
            fontWeight = FontWeight.Medium
        )
    }
}