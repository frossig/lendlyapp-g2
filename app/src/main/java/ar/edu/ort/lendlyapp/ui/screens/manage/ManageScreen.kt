package ar.edu.ort.lendlyapp.ui.screens.manage

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.ort.lendlyapp.ui.components.InitialsAvatar
import ar.edu.ort.lendlyapp.ui.components.MainTabHeader
import ar.edu.ort.lendlyapp.ui.theme.BackgroundCream
import ar.edu.ort.lendlyapp.ui.theme.BackgroundElevated
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary
import ar.edu.ort.lendlyapp.ui.theme.ContentSecondary
import ar.edu.ort.lendlyapp.ui.theme.ContentTertiary
import ar.edu.ort.lendlyapp.ui.theme.InteractiveAccent

@Composable
fun ManageScreen(
    onNotifications: () -> Unit = {},
    onEditAccount: () -> Unit = {},
    onLogout: () -> Unit,
    viewModel: ManageViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
            .statusBarsPadding()
    ) {
        MainTabHeader(onNotifications = onNotifications)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Manage",
                style = MaterialTheme.typography.headlineLarge,
                color = ContentPrimary
            )

            Spacer(Modifier.height(20.dp))
            SectionLabel("Currently using as")
            AccountDetailsCard(fullName = state.fullName.orEmpty(), onEdit = onEditAccount)

            Spacer(Modifier.height(8.dp))
            SectionLabel("General")

            ManageRow(Icons.Outlined.PersonOutline, "Account details", onEditAccount)
            ManageRow(Icons.Outlined.MailOutline, "Receiving by email or phone") { }
            ManageRow(Icons.Outlined.CalendarToday, "Scheduled pay") { }
            ManageRow(Icons.Outlined.Speed, "Credit score") { }
            ManageRow(Icons.Outlined.Settings, "Settings") { }
            ManageRow(Icons.Outlined.Description, "Terms and Conditions") { }
            ManageRow(Icons.Outlined.HelpOutline, "Help") { }

            Spacer(Modifier.height(16.dp))
            ManageRow(Icons.AutoMirrored.Outlined.Logout, "Log Out") {
                viewModel.logout(onLogout)
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Column {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = ContentSecondary,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(ContentTertiary.copy(alpha = 0.2f))
        )
    }
}

@Composable
private fun AccountDetailsCard(fullName: String, onEdit: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        InitialsAvatar(fullName = fullName.ifBlank { "User" }, size = 48.dp)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Account details",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = ContentPrimary
            )
            Text(
                text = "Your personal Account",
                style = MaterialTheme.typography.bodySmall,
                color = ContentSecondary
            )
        }
        EditPill(onClick = onEdit)
    }
}

@Composable
private fun EditPill(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(InteractiveAccent)
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Edit",
            style = MaterialTheme.typography.labelLarge,
            color = ContentPrimary
        )
    }
}

@Composable
private fun ManageRow(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = ContentPrimary,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = ContentPrimary,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
            contentDescription = null,
            tint = ContentSecondary
        )
    }
}
