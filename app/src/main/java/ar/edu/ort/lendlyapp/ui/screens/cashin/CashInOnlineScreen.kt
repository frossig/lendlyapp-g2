package ar.edu.ort.lendlyapp.ui.screens.cashin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ar.edu.ort.lendlyapp.ui.components.AppTopBar
import ar.edu.ort.lendlyapp.ui.components.OptionRow
import ar.edu.ort.lendlyapp.ui.theme.BackgroundCream
import ar.edu.ort.lendlyapp.ui.theme.BackgroundElevated
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary
import ar.edu.ort.lendlyapp.ui.theme.ContentSecondary
import ar.edu.ort.lendlyapp.ui.theme.InteractiveAccent

private val banks = listOf("BPI", "Chinabank", "RCBC", "Unionbank")
private val eWallets = listOf("GCash", "Pay Maya", "PayPal")

@Composable
fun CashInOnlineScreen(
    onProvider: (String) -> Unit,
    onBack: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    val q = query.trim().lowercase()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
    ) {
        AppTopBar(onBack = onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Online Cash-In Options",
                style = MaterialTheme.typography.headlineLarge,
                color = ContentPrimary
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Search...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null,
                        tint = ContentSecondary
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            ProviderGroup(
                label = "BANKS",
                items = banks.filter { it.lowercase().contains(q) },
                onClick = onProvider
            )
            Spacer(Modifier.height(16.dp))
            ProviderGroup(
                label = "E-WALLETS",
                items = eWallets.filter { it.lowercase().contains(q) },
                onClick = onProvider
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ProviderGroup(label: String, items: List<String>, onClick: (String) -> Unit) {
    if (items.isEmpty()) return
    Text(
        text = label,
        style = MaterialTheme.typography.labelMedium,
        color = ContentSecondary,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(BackgroundElevated)
    ) {
        items.forEach { name ->
            OptionRow(
                title = name,
                onClick = { onClick(name) },
                leading = { ProviderAvatar(name) }
            )
        }
    }
}

@Composable
private fun ProviderAvatar(name: String) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(InteractiveAccent),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name.firstOrNull()?.uppercase().orEmpty(),
            style = MaterialTheme.typography.titleMedium,
            color = ContentPrimary
        )
    }
}
