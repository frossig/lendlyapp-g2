package ar.edu.ort.lendlyapp.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ar.edu.ort.lendlyapp.ui.screens.history.HistoryScreen
import ar.edu.ort.lendlyapp.ui.screens.home.HomeScreen
import ar.edu.ort.lendlyapp.ui.screens.loans.LoansScreen
import ar.edu.ort.lendlyapp.ui.screens.manage.ManageScreen
import ar.edu.ort.lendlyapp.ui.screens.shop.ShopScreen
import ar.edu.ort.lendlyapp.ui.theme.BackgroundElevated
import ar.edu.ort.lendlyapp.ui.theme.BackgroundNeutral
import ar.edu.ort.lendlyapp.ui.theme.ContentPrimary
import ar.edu.ort.lendlyapp.ui.theme.ContentTertiary

enum class MainTab(val route: String, val label: String, val icon: ImageVector) {
    HOME("home", "Home", Icons.Outlined.Home),
    LOAN("loan", "Loan", Icons.Outlined.CreditCard),
    SHOP("shop", "Shop", Icons.Outlined.ShoppingBag),
    HISTORY("history", "History", Icons.Outlined.History),
    MANAGE("manage", "Manage", Icons.Outlined.GridView),
}

@Composable
fun MainScaffold(
    onLogout: () -> Unit,
    onCashIn: () -> Unit,
    onNotifications: () -> Unit
) {
    val tabsNavController = rememberNavController()
    val backStack by tabsNavController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = BackgroundElevated) {
                MainTab.entries.forEach { tab ->
                    val selected = currentRoute?.let {
                        backStack?.destination?.hierarchy?.any { it.route == tab.route }
                    } ?: false
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            tabsNavController.navigate(tab.route) {
                                popUpTo(tabsNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(tab.icon, contentDescription = tab.label) },
                        label = { Text(tab.label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = ContentPrimary,
                            selectedTextColor = ContentPrimary,
                            unselectedIconColor = ContentTertiary,
                            unselectedTextColor = ContentTertiary,
                            indicatorColor = BackgroundNeutral
                        )
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = tabsNavController,
            startDestination = MainTab.HOME.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(MainTab.HOME.route) {
                HomeScreen(onCashIn = onCashIn, onNotifications = onNotifications)
            }
            composable(MainTab.LOAN.route) { LoansScreen() }
            composable(MainTab.SHOP.route) { ShopScreen() }
            composable(MainTab.HISTORY.route) { HistoryScreen() }
            composable(MainTab.MANAGE.route) { ManageScreen(onLogout = onLogout) }
        }
    }
}
