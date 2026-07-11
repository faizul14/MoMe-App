package com.faezolmp.momeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.faezolmp.momeapp.core.domain.model.TransactionSource
import com.faezolmp.momeapp.presentation.screen.AddManual.AddManualScreen
import com.faezolmp.momeapp.presentation.screen.Confirm.ConfirmTransactionScreen
import com.faezolmp.momeapp.presentation.screen.Dashboard.DashboardScreen
import com.faezolmp.momeapp.presentation.screen.Dashboard.DashboardViewModel
import com.faezolmp.momeapp.presentation.screen.Detail.DetailViewModel
import com.faezolmp.momeapp.presentation.screen.Detail.TransactionDetailScreen
import com.faezolmp.momeapp.presentation.screen.History.HistoryScreen
import com.faezolmp.momeapp.presentation.screen.History.HistoryViewModel
import com.faezolmp.momeapp.presentation.screen.Manage.ManageBudgetScreen
import com.faezolmp.momeapp.presentation.screen.Manage.ManageCategoryScreen
import com.faezolmp.momeapp.presentation.screen.Onboarding.OnboardingScreen
import com.faezolmp.momeapp.presentation.screen.Scan.ScanScreen
import com.faezolmp.momeapp.presentation.screen.Settings.SettingsScreen
import com.faezolmp.momeapp.presentation.screen.Statistics.StatisticsScreen
import org.koin.androidx.compose.koinViewModel

private fun NavHostController.navigateTopLevel(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun MomeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    sharePayload: SharePayload? = null,
    onShareConsumed: () -> Unit = {}
) {
    LaunchedEffect(sharePayload) {
        if (sharePayload != null) {
            navController.navigate(
                MomeDestination.Confirm.createRoute(
                    sharePayload.amount,
                    sharePayload.attachmentPath,
                    TransactionSource.SHARE.name
                )
            )
            onShareConsumed()
        }
    }
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
            val viewModel = koinViewModel<DashboardViewModel>()
            val state by viewModel.uiState.collectAsStateWithLifecycle()
            DashboardScreen(
                state = state,
                onSeeAllActivities = { navController.navigateTopLevel(MomeDestination.History.route) },
                onActivityClick = { id -> navController.navigate(MomeDestination.Detail.createRoute(id)) },
                onManageCategories = { navController.navigate(MomeDestination.ManageCategory.route) },
                onHistory = { navController.navigateTopLevel(MomeDestination.History.route) },
                onScan = { navController.navigateTopLevel(MomeDestination.Scan.route) },
                onAdd = { navController.navigateTopLevel(MomeDestination.AddManual.route) },
                onManage = { navController.navigateTopLevel(MomeDestination.Settings.route) }
            )
        }

        composable(MomeDestination.AddManual.route) {
            AddManualScreen(
                onBack = { navController.popBackStack() },
                onSaved = {
                    navController.navigate(MomeDestination.Home.route) {
                        popUpTo(MomeDestination.Home.route) { inclusive = true }
                    }
                },
                onDashboard = { navController.navigateTopLevel(MomeDestination.Home.route) },
                onHistory = { navController.navigateTopLevel(MomeDestination.History.route) },
                onScan = { navController.navigateTopLevel(MomeDestination.Scan.route) },
                onManage = { navController.navigateTopLevel(MomeDestination.Settings.route) }
            )
        }

        composable(MomeDestination.ManageBudget.route) {
            ManageBudgetScreen(
                onDashboard = { navController.navigateTopLevel(MomeDestination.Home.route) },
                onHistory = { navController.navigateTopLevel(MomeDestination.History.route) },
                onScan = { navController.navigateTopLevel(MomeDestination.Scan.route) },
                onAdd = { navController.navigateTopLevel(MomeDestination.AddManual.route) },
                onManage = { navController.navigateTopLevel(MomeDestination.Settings.route) }
            )
        }

        composable(MomeDestination.ManageCategory.route) {
            ManageCategoryScreen(
                onDashboard = { navController.navigateTopLevel(MomeDestination.Home.route) },
                onHistory = { navController.navigateTopLevel(MomeDestination.History.route) },
                onScan = { navController.navigateTopLevel(MomeDestination.Scan.route) },
                onAdd = { navController.navigateTopLevel(MomeDestination.AddManual.route) },
                onManage = { navController.navigateTopLevel(MomeDestination.Settings.route) }
            )
        }

        composable(MomeDestination.Scan.route) {
            ScanScreen(
                onClose = { navController.popBackStack() },
                onScanned = { amount, path ->
                    navController.navigate(
                        MomeDestination.Confirm.createRoute(amount, path, TransactionSource.SCAN.name)
                    )
                },
                onDashboard = { navController.navigateTopLevel(MomeDestination.Home.route) },
                onHistory = { navController.navigateTopLevel(MomeDestination.History.route) },
                onAdd = { navController.navigateTopLevel(MomeDestination.AddManual.route) },
                onManage = { navController.navigateTopLevel(MomeDestination.Settings.route) }
            )
        }

        composable(
            route = MomeDestination.Confirm.route,
            arguments = listOf(
                navArgument(MomeDestination.Confirm.ARG_AMOUNT) {
                    type = NavType.LongType
                    defaultValue = 0L
                },
                navArgument(MomeDestination.Confirm.ARG_ATTACHMENT) {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(MomeDestination.Confirm.ARG_SOURCE) {
                    type = NavType.StringType
                    defaultValue = TransactionSource.SCAN.name
                }
            )
        ) { backStackEntry ->
            val args = backStackEntry.arguments
            val amount = args?.getLong(MomeDestination.Confirm.ARG_AMOUNT) ?: 0L
            val attachment = args?.getString(MomeDestination.Confirm.ARG_ATTACHMENT)
                ?.takeIf { it.isNotEmpty() }
            val source = runCatching {
                TransactionSource.valueOf(args?.getString(MomeDestination.Confirm.ARG_SOURCE) ?: "SCAN")
            }.getOrDefault(TransactionSource.SCAN)
            ConfirmTransactionScreen(
                amount = amount,
                attachmentPath = attachment,
                source = source,
                onBack = { navController.popBackStack() },
                onSaved = {
                    navController.navigate(MomeDestination.Home.route) {
                        popUpTo(MomeDestination.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(MomeDestination.History.route) {
            val viewModel = koinViewModel<HistoryViewModel>()
            val state by viewModel.uiState.collectAsStateWithLifecycle()
            HistoryScreen(
                state = state,
                onOpenDetail = { id ->
                    navController.navigate(MomeDestination.Detail.createRoute(id))
                },
                onDashboard = { navController.navigateTopLevel(MomeDestination.Home.route) },
                onScan = { navController.navigateTopLevel(MomeDestination.Scan.route) },
                onAdd = { navController.navigateTopLevel(MomeDestination.AddManual.route) },
                onManage = { navController.navigateTopLevel(MomeDestination.Settings.route) }
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
            val viewModel = koinViewModel<DetailViewModel>()
            LaunchedEffect(id) { viewModel.load(id) }
            val state by viewModel.uiState.collectAsStateWithLifecycle()
            TransactionDetailScreen(
                state = state,
                onBack = { navController.popBackStack() },
                onDelete = {
                    viewModel.delete()
                    navController.popBackStack()
                }
            )
        }

        composable(MomeDestination.Statistics.route) {
            StatisticsScreen(onBack = { navController.popBackStack() })
        }

        composable(MomeDestination.Settings.route) {
            SettingsScreen(
                onCurrency = { navController.navigate(MomeDestination.ManageBudget.route) },
                onDashboard = { navController.navigateTopLevel(MomeDestination.Home.route) },
                onHistory = { navController.navigateTopLevel(MomeDestination.History.route) },
                onScan = { navController.navigateTopLevel(MomeDestination.Scan.route) },
                onAdd = { navController.navigateTopLevel(MomeDestination.AddManual.route) }
            )
        }
    }
}
