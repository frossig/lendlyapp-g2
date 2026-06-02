package ar.edu.ort.lendlyapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.ort.lendlyapp.ui.theme.BackgroundCream
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary

@Composable
fun CategoryCard(
    name: String,
    icon: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .size(width = 110.dp, height = 130.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(BackgroundCream)
            .clickable(onClick = onClick)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = icon ?: "📦",
                fontSize = 40.sp
            )
        }
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = ContentPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
