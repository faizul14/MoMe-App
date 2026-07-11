package com.faezolmp.momeapp.core.notification

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.faezolmp.momeapp.core.domain.usecase.BudgetUseCase
import com.faezolmp.momeapp.core.domain.usecase.TransactionUseCase
import com.faezolmp.momeapp.core.utils.DateUtils
import com.faezolmp.momeapp.core.utils.formatRupiah
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DailyReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KoinComponent {

    private val budgetUseCase: BudgetUseCase by inject()
    private val transactionUseCase: TransactionUseCase by inject()

    override suspend fun doWork(): Result {
        val budget = budgetUseCase.active().first() ?: return Result.success()
        if (budget.eveningReminder) {
            val (start, end) = DateUtils.periodRange(budget.period, budget.weekStartDay, System.currentTimeMillis())
            val spent = transactionUseCase.between(start, end).first()
                .filter { !it.isIncome }
                .sumOf { it.amount }
            val remaining = (budget.amount - spent).coerceAtLeast(0L)
            MomeNotifications.show(
                context = applicationContext,
                id = MomeNotifications.NOTIF_REMINDER,
                title = "Sisa Budget Hari Ini",
                message = "Sisa ${formatRupiah(remaining, budget.currencySymbol)} dari ${formatRupiah(budget.amount, budget.currencySymbol)}."
            )
        }
        return Result.success()
    }
}
