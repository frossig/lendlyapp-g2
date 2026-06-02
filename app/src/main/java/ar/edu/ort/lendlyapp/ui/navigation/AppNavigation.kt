package ar.edu.ort.lendlyapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.ort.lendlyapp.ui.screens.auth.LoginScreen
import ar.edu.ort.lendlyapp.ui.screens.auth.RegisterScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import ar.edu.ort.lendlyapp.ui.screens.cashin.CashInFlow
import ar.edu.ort.lendlyapp.ui.screens.history.TransactionDetailsScreen
import ar.edu.ort.lendlyapp.ui.screens.main.MainScaffold
import ar.edu.ort.lendlyapp.ui.screens.notifications.NotificationsScreen
import ar.edu.ort.lendlyapp.ui.screens.onboarding.OnboardingScreen
import ar.edu.ort.lendlyapp.ui.screens.splash.SplashDestination
import ar.edu.ort.lendlyapp.ui.screens.splash.SplashScreen

object Routes {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val MAIN = "main"
    const val CASH_IN = "cashIn"
    const val NOTIFICATIONS = "notifications"
    const val TRANSACTION_DETAILS = "transactionDetails/{txId}"
    fun transactionDetails(id: String) = "transactionDetails/$id"
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(
                onDecided = { dest ->
                    val target = when (dest) {
                        SplashDestination.MAIN -> Routes.MAIN
                        SplashDestination.LOGIN -> Routes.LOGIN
                        SplashDestination.ONBOARDING -> Routes.ONBOARDING
                    }
                    navController.toRoot(target)
                }
            )
        }
        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                onGoToLogin = { navController.navigate(Routes.LOGIN) },
                onGoToRegister = { navController.navigate(Routes.REGISTER) }
            )
        }
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoggedIn = { navController.toRoot(Routes.MAIN) },
                onGoToRegister = { navController.navigate(Routes.REGISTER) }
            )
        }
        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegistered = { navController.toRoot(Routes.MAIN) },
                onGoToLogin = { navController.navigate(Routes.LOGIN) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.MAIN) {
            MainScaffold(
                onLogout = { navController.toRoot(Routes.LOGIN) },
                onCashIn = { navController.navigate(Routes.CASH_IN) },
                onNotifications = { navController.navigate(Routes.NOTIFICATIONS) },
                onTransactionDetails = { id ->
                    navController.navigate(Routes.transactionDetails(id))
                }
            )
        }
        composable(
            route = Routes.TRANSACTION_DETAILS,
            arguments = listOf(navArgument("txId") { type = NavType.StringType })
        ) {
            TransactionDetailsScreen(onClose = { navController.popBackStack() })
        }
        composable(Routes.CASH_IN) {
            CashInFlow(onClose = { navController.popBackStack() })
        }
        composable(Routes.NOTIFICATIONS) {
            NotificationsScreen(onBack = { navController.popBackStack() })
        }
    }
}

private fun NavHostController.toRoot(route: String) {
    navigate(route) {
        popUpTo(graph.startDestinationId) { inclusive = true }
        launchSingleTop = true
    }
}
