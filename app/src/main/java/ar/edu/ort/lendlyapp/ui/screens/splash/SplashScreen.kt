package ar.edu.ort.lendlyapp.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import ar.edu.ort.lendlyapp.R
import ar.edu.ort.lendlyapp.data.local.SessionManager
import ar.edu.ort.lendlyapp.ui.theme.BackgroundNeutral
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import javax.inject.Inject

enum class SplashDestination { MAIN, LOGIN, ONBOARDING }

@Composable
fun SplashScreen(
    onDecided: (SplashDestination) -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        delay(1200L)
        onDecided(viewModel.decideDestination())
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundNeutral)
            .systemBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo_lendly_icon),
            contentDescription = "LendlyApp",
            modifier = Modifier.size(width = 108.dp, height = 130.dp)
        )
    }
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    suspend fun decideDestination(): SplashDestination {
        val token = sessionManager.authToken.first()
        if (!token.isNullOrBlank()) return SplashDestination.MAIN

        val savedPhone = sessionManager.savedPhone.first()
        val savedName = sessionManager.savedFullName.first()
        return if (!savedPhone.isNullOrBlank() && !savedName.isNullOrBlank()) {
            SplashDestination.LOGIN
        } else {
            SplashDestination.ONBOARDING
        }
    }
}
