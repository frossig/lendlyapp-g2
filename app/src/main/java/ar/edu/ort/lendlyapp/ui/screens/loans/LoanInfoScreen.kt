package ar.edu.ort.lendlyapp.ui.screens.loans

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ar.edu.ort.lendlyapp.ui.components.MainTabHeader
import ar.edu.ort.lendlyapp.ui.components.PrimaryButton
import ar.edu.ort.lendlyapp.ui.components.formatPhp
import ar.edu.ort.lendlyapp.ui.theme.BackgroundCream
import ar.edu.ort.lendlyapp.ui.theme.BackgroundNeutral
import ar.edu.ort.lendlyapp.ui.theme.BackgroundScreen
import ar.edu.ort.lendlyapp.ui.theme.ContentLink
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary
import ar.edu.ort.lendlyapp.ui.theme.ContentSecondary
import ar.edu.ort.lendlyapp.ui.theme.InteractiveAccent
import ar.edu.ort.lendlyapp.ui.theme.InteractivePrimary

// Valores tomados del diseno de Figma (no provienen de la API).
private const val MAX_BORROW = 30_000.0
private const val PAYABLE_LABEL = "6 - 12"
private const val INTEREST_LABEL = "1.99%"
private const val PROCESS_FEE_LABEL = "3%"

private data class HowItWorksItem(
    val icon: ImageVector,
    val title: String,
    val description: String
)

private val howItWorksItems = listOf(
    HowItWorksItem(
        Icons.Outlined.TrendingUp,
        "Keep your credit score high",
        "The offered loan amount is based on your credit score"
    ),
    HowItWorksItem(
        Icons.Outlined.Bolt,
        "Get instant approval",
        "Everything we need to process is already in the application"
    ),
    HowItWorksItem(
        Icons.Outlined.Payments,
        "Easy payments option available",
        "Skip the queue and pay your due on the application"
    ),
    HowItWorksItem(
        Icons.Outlined.VerifiedUser,
        "Safe and secure",
        "Rayland is working with trusted partners to provide this services"
    )
)

@Composable
fun LoanInfoScreen(
    onApply: () -> Unit,
    onViewActiveLoans: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundScreen)
    ) {
        MainTabHeader()

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            HeroCard()

            BorrowSection(onViewActiveLoans = onViewActiveLoans)

            Text(
                text = "How it works",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = ContentPrimary
            )

            // Grilla 2x2 sin LazyVerticalGrid (evita scroll anidado).
            howItWorksItems.chunked(2).forEach { rowItems ->
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    rowItems.forEach { item ->
                        HowItWorksCard(item = item, modifier = Modifier.weight(1f))
                    }
                    if (rowItems.size == 1) Spacer(Modifier.weight(1f))
                }
            }

            Spacer(Modifier.height(4.dp))
        }

        Column(modifier = Modifier.padding(16.dp)) {
            PrimaryButton(text = "Get This Loan", onClick = onApply)
        }
    }
}

@Composable
private fun HeroCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(InteractiveAccent)
            .padding(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(BackgroundScreen)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Limited Time Offer",
                    style = MaterialTheme.typography.labelMedium,
                    color = ContentPrimary
                )
            }
            Text(
                text = "Safe and\nsecure loans",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = InteractivePrimary
            )
            Text(
                text = "All here in Rayland",
                style = MaterialTheme.typography.bodyMedium,
                color = InteractivePrimary
            )
        }
    }
}

@Composable
private fun BorrowSection(onViewActiveLoans: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "You can borrow up to",
            style = MaterialTheme.typography.bodyMedium,
            color = ContentSecondary
        )
        Text(
            text = formatPhp(MAX_BORROW),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = ContentPrimary
        )
        Text(
            text = "*Subject to evaluation",
            style = MaterialTheme.typography.bodySmall,
            color = ContentSecondary
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Loan Details",
                style = MaterialTheme.typography.titleMedium,
                color = ContentPrimary
            )
            Text(
                text = "What is this?",
                style = MaterialTheme.typography.bodyMedium,
                color = ContentLink
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(BackgroundCream)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DetailItem("Payable in", PAYABLE_LABEL, "months")
            DetailItem("Interest Rate", INTEREST_LABEL, "ave per mo.")
            DetailItem("Process Fee", PROCESS_FEE_LABEL, "as low as")
        }

        Text(
            text = "View my active loans",
            style = MaterialTheme.typography.titleMedium,
            color = ContentLink,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable(onClick = onViewActiveLoans)
                .padding(vertical = 4.dp)
        )
    }
}

@Composable
private fun DetailItem(label: String, value: String, sub: String) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = ContentSecondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = ContentPrimary
        )
        Text(
            text = sub,
            style = MaterialTheme.typography.bodySmall,
            color = ContentSecondary
        )
    }
}

@Composable
private fun HowItWorksCard(item: HowItWorksItem, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(BackgroundCream)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(BackgroundNeutral),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = InteractivePrimary,
                modifier = Modifier.size(22.dp)
            )
        }
        Text(
            text = item.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = ContentPrimary
        )
        Text(
            text = item.description,
            style = MaterialTheme.typography.bodySmall,
            color = ContentSecondary
        )
    }
}