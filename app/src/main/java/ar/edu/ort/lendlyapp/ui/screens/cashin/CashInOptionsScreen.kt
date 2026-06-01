package ar.edu.ort.lendlyapp.ui.screens.cashin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ar.edu.ort.lendlyapp.ui.components.AppTopBar
import ar.edu.ort.lendlyapp.ui.components.MintCircleIcon
import ar.edu.ort.lendlyapp.ui.components.OptionRow
import ar.edu.ort.lendlyapp.ui.theme.BackgroundCream
import ar.edu.ort.lendlyapp.ui.theme.BackgroundElevated
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary
import ar.edu.ort.lendlyapp.ui.theme.InteractivePrimary

@Composable
fun CashInOptionsScreen(
    onOnlineBanking: () -> Unit,
    onOverCounter: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
    ) {
        AppTopBar(onBack = onBack, onInfo = {})

        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Cash-In Options",
                style = MaterialTheme.typography.headlineLarge,
                color = ContentPrimary
            )
            Spacer(Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(BackgroundElevated)
            ) {
                OptionRow(
                    title = "Online Banking",
                    subtitle = "Pay via other banks or e-wallet",
                    onClick = onOnlineBanking,
                    leading = {
                        MintCircleIcon {
                            Icon(
                                imageVector = Icons.Outlined.AccountBalanceWallet,
                                contentDescription = null,
                                tint = InteractivePrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                )
                OptionRow(
                    title = "Over-the-counter",
                    subtitle = "Pay in cash",
                    onClick = onOverCounter,
                    leading = {
                        MintCircleIcon {
                            Icon(
                                imageVector = Icons.Outlined.LocationOn,
                                contentDescription = null,
                                tint = InteractivePrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                )
            }
        }
    }
}
