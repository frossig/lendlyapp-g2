package ar.edu.ort.lendlyapp.ui.screens.loans

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

private object LoanRoutes {
    const val INFO = "loanInfo"
    const val FORM = "loanForm"
    const val SUCCESS = "loanSuccess"
    const val ACTIVE = "activeLoans"
}

@Composable
fun LoansScreen(
    viewModel: LoanViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = LoanRoutes.INFO) {
        composable(LoanRoutes.INFO) {
            LoanInfoScreen(
                onApply = { navController.navigate(LoanRoutes.FORM) },
                onViewActiveLoans = { navController.navigate(LoanRoutes.ACTIVE) }
            )
        }
        composable(LoanRoutes.FORM) {
            LoanFormScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onSuccess = {
                    navController.navigate(LoanRoutes.SUCCESS) {
                        popUpTo(LoanRoutes.INFO)
                    }
                }
            )
        }
        composable(LoanRoutes.SUCCESS) {
            LoanSuccessScreen(
                viewModel = viewModel,
                onDone = {
                    viewModel.resetApply()
                    navController.navigate(LoanRoutes.ACTIVE) {
                        popUpTo(LoanRoutes.INFO)
                    }
                }
            )
        }
        composable(LoanRoutes.ACTIVE) {
            ActiveLoansScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}