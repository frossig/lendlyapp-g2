package com.example.simulacro.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simulacro.ui.theme.SimulacroTheme

@Composable
fun DetailScreen(
    id: String,
    contentPadding: PaddingValues = PaddingValues(),
    onBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(text = "Detalle: $id")
        Button(onClick = onBack) { Text("Volver") }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailScreenPreview() {
    SimulacroTheme { DetailScreen(id = "123") }
}
