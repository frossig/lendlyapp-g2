package ar.edu.ort.lendlyapp.ui.screens.cashin

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun CashInFlow(onClose: () -> Unit) {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = "options"
    ) {
        composable("options") {
            CashInOptionsScreen(
                onOnlineBanking = { nav.navigate("online") },
                onOverCounter = { nav.navigate("otc") },
                onBack = onClose
            )
        }
        composable("online") {
            CashInOnlineScreen(
                onProvider = { provider -> nav.navigate("amount/$provider") },
                onBack = { nav.popBackStack() }
            )
        }
        composable("otc") {
            CashInOTCScreen(
                onProvider = { provider -> nav.navigate("amount/$provider") },
                onBack = { nav.popBackStack() }
            )
        }
        composable("amount/{provider}") { entry ->
            val provider = entry.arguments?.getString("provider").orEmpty()
            CashInAmountScreen(
                provider = provider,
                onNext = { amount -> nav.navigate("success/$provider/$amount") },
                onBack = { nav.popBackStack() }
            )
        }
        composable("success/{provider}/{amount}") { entry ->
            val provider = entry.arguments?.getString("provider").orEmpty()
            val amount = entry.arguments?.getString("amount")?.toDoubleOrNull() ?: 0.0
            CashInSuccessScreen(
                provider = provider,
                amount = amount,
                onDone = onClose
            )
        }
    }
}
