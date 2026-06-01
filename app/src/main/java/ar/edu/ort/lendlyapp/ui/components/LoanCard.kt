package ar.edu.ort.lendlyapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ar.edu.ort.lendlyapp.ui.theme.BackgroundCream
import ar.edu.ort.lendlyapp.ui.theme.BackgroundElevated
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary
import ar.edu.ort.lendlyapp.ui.theme.ContentSecondary
import coil.compose.AsyncImage

@Composable
fun LoanCard(
    lender: String,
    lenderLogo: String?,
    amount: Double,
    nextPaymentLabel: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(BackgroundCream)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(BackgroundElevated),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = lender.firstOrNull()?.uppercase() ?: "?",
                style = MaterialTheme.typography.titleMedium,
                color = ContentPrimary
            )
            if (!lenderLogo.isNullOrBlank()) {
                AsyncImage(
                    model = lenderLogo,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = lender,
                style = MaterialTheme.typography.titleMedium,
                color = ContentPrimary,
                modifier = Modifier.weight(1f)
            )
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = formatPhp(amount),
                    style = MaterialTheme.typography.titleMedium,
                    color = ContentPrimary
                )
                if (!nextPaymentLabel.isNullOrBlank()) {
                    Text(
                        text = nextPaymentLabel,
                        style = MaterialTheme.typography.bodySmall,
                        color = ContentSecondary
                    )
                }
            }
        }
    }
}
