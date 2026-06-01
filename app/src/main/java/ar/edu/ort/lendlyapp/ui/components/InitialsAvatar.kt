package ar.edu.ort.lendlyapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ar.edu.ort.lendlyapp.ui.theme.BackgroundNeutral
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary

@Composable
fun InitialsAvatar(
    fullName: String,
    size: Dp = 48.dp,
    modifier: Modifier = Modifier
) {
    val initials = fullName
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .mapNotNull { it.firstOrNull()?.uppercaseChar() }
        .joinToString("")

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(BackgroundNeutral),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            style = MaterialTheme.typography.labelLarge,
            color = ContentPrimary
        )
    }
}
