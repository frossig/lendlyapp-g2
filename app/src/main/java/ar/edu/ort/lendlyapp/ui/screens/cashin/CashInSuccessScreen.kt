package ar.edu.ort.lendlyapp.ui.screens.cashin

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import ar.edu.ort.lendlyapp.ui.components.PrimaryButton
import ar.edu.ort.lendlyapp.ui.components.formatPhp
import ar.edu.ort.lendlyapp.ui.theme.BackgroundCream
import ar.edu.ort.lendlyapp.ui.theme.BackgroundElevated
import ar.edu.ort.lendlyapp.ui.theme.BalanceText
import ar.edu.ort.lendlyapp.ui.theme.BorderNeutral
import ar.edu.ort.lendlyapp.ui.theme.ContentLink
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary
import ar.edu.ort.lendlyapp.ui.theme.ContentSecondary
import ar.edu.ort.lendlyapp.ui.theme.InteractiveAccent

@Composable
fun CashInSuccessScreen(
    provider: String,
    amount: Double,
    onDone: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        TopActions(onClose = onDone)

        // Hero section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundCream)
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
                    imageVector = Icons.Outlined.Add,
                    contentDescription = null,
                    tint = ContentPrimary,
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = "Added to your account",
                style = MaterialTheme.typography.bodyLarge,
                color = ContentSecondary
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "${formatPhp(amount)} PHP",
                style = BalanceText,
                color = ContentPrimary
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "From $provider",
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
                    text = "Cash-In",
                    style = MaterialTheme.typography.labelLarge,
                    color = ContentPrimary
                )
            }
        }

        // White panel with transaction details + bottom button
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(BackgroundElevated)
                .padding(24.dp)
        ) {
            Text(
                text = "Transaction Details",
                style = MaterialTheme.typography.headlineMedium,
                color = ContentPrimary
            )
            Spacer(Modifier.height(16.dp))

            DetailRow(label = "Transfer Fee", value = "-₱15.00")
            Spacer(Modifier.height(12.dp))
            DetailRow(label = "Date & Time", value = "Jun 1, 2026 9:12 AM")
            Spacer(Modifier.height(12.dp))
            DetailRow(
                label = "Transaction Number",
                value = "#200412312551",
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
                    text = "Need help?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = ContentSecondary
                )
                Text(
                    text = "Go to Help Center",
                    style = MaterialTheme.typography.labelLarge,
                    color = ContentLink,
                    textDecoration = TextDecoration.Underline
                )
            }

            Spacer(Modifier.weight(1f))

            PrimaryButton(text = "Done", onClick = onDone)
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
