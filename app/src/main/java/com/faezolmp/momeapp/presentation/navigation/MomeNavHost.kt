package com.faezolmp.momeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.faezolmp.momeapp.presentation.screen.AddManual.AddManualScreen
import com.faezolmp.momeapp.presentation.screen.Confirm.ConfirmTransactionScreen
import com.faezolmp.momeapp.presentation.screen.Dashboard.DashboardScreen
import com.faezolmp.momeapp.presentation.screen.Dashboard.sampleDashboardState
import com.faezolmp.momeapp.presentation.screen.Detail.TransactionDetailScreen
import com.faezolmp.momeapp.presentation.screen.History.HistoryScreen
import com.faezolmp.momeapp.presentation.screen.Manage.ManageBudgetScreen
import com.faezolmp.momeapp.presentation.screen.Onboarding.OnboardingScreen
import com.faezolmp.momeapp.presentation.screen.Scan.ScanScreen
import com.faezolmp.momeapp.presentation.screen.Settings.SettingsScreen
import com.faezolmp.momeapp.presentation.screen.Statistics.StatisticsScreen

@Composable
fun MomeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = MomeDestination.Home.route,
        modifier = modifier
    ) {
        composable(MomeDestination.Onboarding.route) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(MomeDestination.Home.route) {
                        popUpTo(MomeDestination.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(MomeDestination.Home.route) {
            DashboardScreen(
                state = sampleDashboardState(),
                onSeeAllActivities = { navController.navigate(MomeDestination.History.route) },
                onActivityClick = { navController.navigate(MomeDestination.Detail.createRoute(1L)) },
                onHistory = { navController.navigate(MomeDestination.History.route) },
                onScan = { navController.navigate(MomeDestination.Scan.route) },
                onAdd = { navController.navigate(MomeDestination.AddManual.route) },
                onManage = { navController.navigate(MomeDestination.ManageBudget.route) }
            )
        }

        composable(MomeDestination.AddManual.route) {
            AddManualScreen(
                onBack = { navController.popBackStack() },
                onSave = {
                    navController.navigate(MomeDestination.Home.route) {
                        popUpTo(MomeDestination.Home.route) { inclusive = true }
                    }
                },
                onDashboard = { navController.navigate(MomeDestination.Home.route) },
                onHistory = { navController.navigate(MomeDestination.History.route) },
                onScan = { navController.navigate(MomeDestination.Scan.route) },
                onManage = { navController.navigate(MomeDestination.ManageBudget.route) }
            )
        }

        composable(MomeDestination.ManageBudget.route) {
            ManageBudgetScreen(
                onDashboard = { navController.navigate(MomeDestination.Home.route) },
                onHistory = { navController.navigate(MomeDestination.History.route) },
                onScan = { navController.navigate(MomeDestination.Scan.route) },
                onAdd = { navController.navigate(MomeDestination.AddManual.route) }
            )
        }

        composable(MomeDestination.Scan.route) {
            ScanScreen(
                onClose = { navController.popBackStack() },
                onDashboard = { navController.navigate(MomeDestination.Home.route) },
                onHistory = { navController.navigate(MomeDestination.History.route) },
                onAdd = { navController.navigate(MomeDestination.AddManual.route) },
                onManage = { navController.navigate(MomeDestination.ManageBudget.route) }
            )
        }

        composable(MomeDestination.Confirm.route) {
            ConfirmTransactionScreen(
                onSave = {
                    navController.navigate(MomeDestination.Home.route) {
                        popUpTo(MomeDestination.Home.route) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(MomeDestination.History.route) {
            HistoryScreen(
                onOpenDetail = { id ->
                    navController.navigate(MomeDestination.Detail.createRoute(id))
                },
                onDashboard = { navController.navigate(MomeDestination.Home.route) },
                onScan = { navController.navigate(MomeDestination.Scan.route) },
                onAdd = { navController.navigate(MomeDestination.AddManual.route) },
                onManage = { navController.navigate(MomeDestination.ManageBudget.route) }
            )
        }

        composable(
            route = MomeDestination.Detail.route,
            arguments = listOf(
                navArgument(MomeDestination.Detail.ARG_TRANSACTION_ID) {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments
                ?.getLong(MomeDestination.Detail.ARG_TRANSACTION_ID) ?: 0L
            TransactionDetailScreen(
                transactionId = id,
                onBack = { navController.popBackStack() }
            )
        }

        composable(MomeDestination.Statistics.route) {
            StatisticsScreen(onBack = { navController.popBackStack() })
        }

        composable(MomeDestination.Settings.route) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
    }
}
