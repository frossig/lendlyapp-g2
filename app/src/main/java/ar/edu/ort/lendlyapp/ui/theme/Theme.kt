package ar.edu.ort.lendlyapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LendlyColorScheme = lightColorScheme(
    primary = InteractivePrimary,
    onPrimary = BaseContrast,
    secondary = InteractiveAccent,
    onSecondary = ContentPrimary,
    tertiary = InteractiveSecondary,
    onTertiary = BaseContrast,
    background = BackgroundScreen,
    onBackground = ContentPrimary,
    surface = BackgroundElevated,
    onSurface = ContentPrimary,
    surfaceVariant = BackgroundNeutral,
    onSurfaceVariant = ContentSecondary,
    outline = BorderNeutral,
    error = SentimentNegative,
    onError = BaseContrast
)

@Composable
fun LendlyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LendlyColorScheme,
        typography = Typography,
        content = content
    )
}
