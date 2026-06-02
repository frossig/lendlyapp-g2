package ar.edu.ort.lendlyapp.ui.screens.history

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.ort.lendlyapp.data.remote.dto.TransactionDto
import ar.edu.ort.lendlyapp.ui.components.formatPhp
import ar.edu.ort.lendlyapp.ui.theme.BackgroundCream
import ar.edu.ort.lendlyapp.ui.theme.BackgroundElevated
import ar.edu.ort.lendlyapp.ui.theme.BalanceText
import ar.edu.ort.lendlyapp.ui.theme.BorderNeutral
import ar.edu.ort.lendlyapp.ui.theme.ContentLink
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary
import ar.edu.ort.lendlyapp.ui.theme.ContentSecondary
import ar.edu.ort.lendlyapp.ui.theme.InteractiveAccent
import ar.edu.ort.lendlyapp.ui.theme.InteractivePrimary
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.abs

@Composable
fun TransactionDetailsScreen(
    onClose: () -> Unit,
    viewModel: TransactionDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        TopActions(onClose = onClose)

        when (val s = state) {
            TransactionDetailsUiState.Loading -> LoadingBox()
            is TransactionDetailsUiState.Error -> ErrorBox(message = s.message)
            is TransactionDetailsUiState.Success -> DetailsContent(tx = s.transaction)
        }
    }
}

@Composable
private fun LoadingBox() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = InteractiveAccent)
    }
}

@Composable
private fun ErrorBox(message: String) {
    Box(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message, color = ContentSecondary)
    }
}

@Composable
private fun DetailsContent(tx: TransactionDto) {
    val friendly = friendlyTitle(tx.type)
    val partner = extractPartner(tx.title)
    val pillLabel = pillForType(tx.type)
    val isPositive = tx.amount > 0
    val partnerPrefix = if (isPositive) "From" else "To"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(InteractiveAccent),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowUpward,
                contentDescription = null,
                tint = InteractivePrimary,
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(Modifier.height(12.dp))
        Text(
            text = friendly,
            style = MaterialTheme.typography.bodyLarge,
            color = ContentSecondary
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "${formatPhp(abs(tx.amount))} ${tx.currency.orEmpty()}".trim(),
            style = BalanceText,
            color = ContentPrimary
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "$partnerPrefix $partner",
            style = MaterialTheme.typography.bodyMedium,
            color = ContentSecondary
        )
        Spacer(Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .border(1.dp, BorderNeutral, CircleShape)
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text(
                text = pillLabel,
                style = MaterialTheme.typography.labelLarge,
                color = ContentPrimary
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundElevated)
            .padding(24.dp)
    ) {
        Text(
            text = "Transaction Details",
            style = MaterialTheme.typography.headlineMedium,
            color = ContentPrimary
        )
        Spacer(Modifier.height(16.dp))

        DetailRow(label = "Fee", value = formatPhp(estimateFee(abs(tx.amount))))
        Spacer(Modifier.height(12.dp))
        DetailRow(label = "Date & Time", value = formatDateTime(tx.date))
        Spacer(Modifier.height(12.dp))
        DetailRow(
            label = "Transaction Number",
            value = "#${tx.referenceNumber ?: tx.id}",
            valueIsLink = true
        )

        Spacer(Modifier.height(24.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(BorderNeutral)
        )
        Spacer(Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Didn't find what you were looking for?",
                style = MaterialTheme.typography.bodyMedium,
                color = ContentSecondary,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Go to Help Center",
                style = MaterialTheme.typography.labelLarge,
                color = ContentLink,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Composable
private fun TopActions(onClose: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(BackgroundElevated),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = onClose, modifier = Modifier.size(36.dp)) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Close",
                    tint = ContentPrimary
                )
            }
        }
        Spacer(Modifier.weight(1f))
        IconButton(onClick = {}, modifier = Modifier.size(36.dp)) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Info",
                tint = ContentPrimary
            )
        }
        IconButton(onClick = {}, modifier = Modifier.size(36.dp)) {
            Icon(
                imageVector = Icons.Outlined.MoreHoriz,
                contentDescription = "More",
                tint = ContentPrimary
            )
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String, valueIsLink: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = ContentSecondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = if (valueIsLink) ContentLink else ContentPrimary,
            textDecoration = if (valueIsLink) TextDecoration.Underline else null
        )
    }
}

private fun friendlyTitle(type: String): String = when (type) {
    "LOAN_PAYMENT" -> "Paid this month"
    "CASH_IN" -> "Added to your account"
    "LOAN_DISBURSEMENT" -> "Loan disbursement"
    else -> type.lowercase().replaceFirstChar { it.uppercase() }
}

private fun pillForType(type: String): String = when (type) {
    "LOAN_PAYMENT" -> "Paid Bills"
    "CASH_IN" -> "Cash-In"
    "LOAN_DISBURSEMENT" -> "Loan"
    else -> "Transaction"
}

private fun extractPartner(title: String): String {
    val parts = title.split("—")
    return parts.getOrNull(1)?.trim() ?: parts.first().trim()
}

private fun estimateFee(amount: Double): Double = (amount * 0.025).let {
    // Fee mock: ~2.5% del monto, redondeado a entero
    kotlin.math.round(it)
}

private fun formatDateTime(iso: String): String = try {
    OffsetDateTime.parse(iso)
        .format(DateTimeFormatter.ofPattern("MMM d, yyyy h:mm a", Locale.ENGLISH))
} catch (_: Throwable) {
    iso
}
