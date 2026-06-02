package ar.edu.ort.lendlyapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ar.edu.ort.lendlyapp.R
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary

@Composable
fun MainTabHeader(
    onProfile: () -> Unit = {},
    onNotifications: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onProfile, modifier = Modifier.size(40.dp)) {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = "Profile",
                tint = ContentPrimary
            )
        }
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(R.drawable.logo_lendly_small),
                contentDescription = "LendlyApp",
                modifier = Modifier.size(width = 70.dp, height = 26.dp)
            )
        }
        IconButton(onClick = onNotifications, modifier = Modifier.size(40.dp)) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notifications",
                tint = ContentPrimary
            )
        }
    }
}
