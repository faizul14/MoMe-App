package com.faezolmp.momeapp.presentation.navigation

sealed class MomeDestination(val route: String) {
    object Onboarding : MomeDestination("onboarding")
    object Home : MomeDestination("home")
    object AddManual : MomeDestination("add_manual")
    object Scan : MomeDestination("scan")
    object Confirm : MomeDestination("confirm")
    object History : MomeDestination("history")
    object Statistics : MomeDestination("statistics")
    object Settings : MomeDestination("settings")

    object Detail : MomeDestination("detail/{transactionId}") {
        const val ARG_TRANSACTION_ID = "transactionId"
        fun createRoute(transactionId: Long): String = "detail/$transactionId"
    }
}
