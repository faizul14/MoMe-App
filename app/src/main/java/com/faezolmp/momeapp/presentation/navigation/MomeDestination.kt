package com.faezolmp.momeapp.presentation.navigation

import android.net.Uri

sealed class MomeDestination(val route: String) {
    object Onboarding : MomeDestination("onboarding")
    object Home : MomeDestination("home")
    object AddManual : MomeDestination("add_manual")
    object Scan : MomeDestination("scan")

    object Confirm : MomeDestination("confirm?amount={amount}&attachment={attachment}&source={source}") {
        const val ARG_AMOUNT = "amount"
        const val ARG_ATTACHMENT = "attachment"
        const val ARG_SOURCE = "source"
        fun createRoute(amount: Long, attachment: String?, source: String): String {
            val encoded = attachment?.let { Uri.encode(it) } ?: ""
            return "confirm?amount=$amount&attachment=$encoded&source=$source"
        }
    }
    object History : MomeDestination("history")
    object Statistics : MomeDestination("statistics")
    object Settings : MomeDestination("settings")
    object ManageBudget : MomeDestination("manage_budget")
    object ManageCategory : MomeDestination("manage_category")

    object Detail : MomeDestination("detail/{transactionId}") {
        const val ARG_TRANSACTION_ID = "transactionId"
        fun createRoute(transactionId: Long): String = "detail/$transactionId"
    }

    object Edit : MomeDestination("edit/{transactionId}") {
        const val ARG_TRANSACTION_ID = "transactionId"
        fun createRoute(transactionId: Long): String = "edit/$transactionId"
    }

    object EditProfile : MomeDestination("edit_profile")
}
