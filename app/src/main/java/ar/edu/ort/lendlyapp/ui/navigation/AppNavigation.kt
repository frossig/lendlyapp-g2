package ar.edu.ort.lendlyapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.ort.lendlyapp.ui.screens.auth.LoginScreen
import ar.edu.ort.lendlyapp.ui.screens.auth.RegisterScreen
import ar.edu.ort.lendlyapp.ui.screens.main.MainScaffold
import ar.edu.ort.lendlyapp.ui.screens.onboarding.OnboardingScreen
import ar.edu.ort.lendlyapp.ui.screens.splash.SplashScreen

/**
 * Rutas raíz de la app.
 *
 * Splash es el destino inicial: ahí se decide si la sesión existe (→ Main)
 * o no (→ Onboarding). Cuando la app entra a Main, se monta otro NavHost
 * INTERNO con las 5 pestañas (ver MainScaffold).
 */
object Routes {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val MAIN = "main"
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
                onAuthenticated = { navController.toRoot(Routes.MAIN) },
                onUnauthenticated = { navController.toRoot(Routes.ONBOARDING) }
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
                onGoToRegister = { navController.navigate(Routes.REGISTER) },
                onBack = { navController.popBackStack() }
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
                onLogout = { navController.toRoot(Routes.ONBOARDING) }
            )
        }
    }
}

/** Reemplaza el back stack: útil para "ir a Home después del login y que no pueda volver". */
private fun NavHostController.toRoot(route: String) {
    navigate(route) {
        popUpTo(graph.startDestinationId) { inclusive = true }
        launchSingleTop = true
    }
}
