package com.faezolmp.momeapp.core.utils

import com.faezolmp.momeapp.core.domain.model.BudgetPeriod
import java.util.Calendar

object DateUtils {

    fun periodRange(period: BudgetPeriod, weekStartDay: Int, now: Long): Pair<Long, Long> {
        return when (period) {
            BudgetPeriod.DAILY -> startOfDay(now) to endOfDay(now)
            BudgetPeriod.WEEKLY -> startOfWeek(now, weekStartDay) to endOfWeek(now, weekStartDay)
        }
    }

    private fun startOfDay(time: Long): Long {
        val calendar = baseCalendar(time)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    private fun endOfDay(time: Long): Long {
        return startOfDay(time) + 24L * 60L * 60L * 1000L - 1L
    }

    private fun startOfWeek(time: Long, weekStartDay: Int): Long {
        val calendar = baseCalendar(startOfDay(time))
        val calendarWeekStart = toCalendarDay(weekStartDay)
        while (calendar.get(Calendar.DAY_OF_WEEK) != calendarWeekStart) {
            calendar.add(Calendar.DAY_OF_MONTH, -1)
        }
        return calendar.timeInMillis
    }

    private fun endOfWeek(time: Long, weekStartDay: Int): Long {
        return startOfWeek(time, weekStartDay) + 7L * 24L * 60L * 60L * 1000L - 1L
    }

    private fun baseCalendar(time: Long): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        return calendar
    }

    private fun toCalendarDay(weekStartDay: Int): Int {
        return if (weekStartDay == 7) Calendar.SUNDAY else weekStartDay + 1
    }
}
