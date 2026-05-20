package com.example.simulacro.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simulacro.ui.screens.detail.DetailScreen
import com.example.simulacro.ui.screens.home.HomeScreen

/**
 * Rutas de navegación.
 *
 * Mantener los nombres acá centralizado evita typos por hardcodear el string en
 * cada `navigate(...)`.
 */
object Routes {
    const val HOME = "home"
    const val DETAIL = "detail/{id}"

    fun detail(id: String) = "detail/$id"
}

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues()
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                contentPadding = contentPadding,
                onItemClick = { id -> navController.navigate(Routes.detail(id)) }
            )
        }
        composable(Routes.DETAIL) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id").orEmpty()
            DetailScreen(
                id = id,
                contentPadding = contentPadding,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
