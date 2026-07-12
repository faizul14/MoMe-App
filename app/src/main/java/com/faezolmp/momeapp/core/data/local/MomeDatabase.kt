package com.faezolmp.momeapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.faezolmp.momeapp.core.data.local.dao.BudgetDao
import com.faezolmp.momeapp.core.data.local.dao.CategoryDao
import com.faezolmp.momeapp.core.data.local.dao.ProfileDao
import com.faezolmp.momeapp.core.data.local.dao.TransactionDao
import com.faezolmp.momeapp.core.data.local.entity.BudgetEntity
import com.faezolmp.momeapp.core.data.local.entity.CategoryEntity
import com.faezolmp.momeapp.core.data.local.entity.ProfileEntity
import com.faezolmp.momeapp.core.data.local.entity.TransactionEntity

@Database(
    entities = [TransactionEntity::class, CategoryEntity::class, BudgetEntity::class, ProfileEntity::class],
    version = 2,
    exportSchema = false
)
abstract class MomeDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao
    abstract fun budgetDao(): BudgetDao
    abstract fun profileDao(): ProfileDao

    companion object {
        const val DATABASE_NAME = "mome.db"

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS profile (id INTEGER NOT NULL PRIMARY KEY, name TEXT NOT NULL, email TEXT NOT NULL)"
                )
                db.execSQL("INSERT OR IGNORE INTO profile (id, name, email) VALUES (1, 'Fiscal Harmony', '')")
            }
        }

        val seedCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.execSQL(
                    "INSERT INTO budget (id, period, amount, weekStartDay, currencyCode, currencySymbol, decimalDigits, overLimitAlert, eveningReminder, warningThreshold, isActive) " +
                        "VALUES (1, 'DAILY', 150000, 1, 'IDR', 'Rp', 0, 1, 0, 0.8, 1)"
                )
                db.execSQL("INSERT INTO profile (id, name, email) VALUES (1, 'Fiscal Harmony', '')")
                val categories = listOf(
                    Triple("Makan & Minum", "food", "#E8912E"),
                    Triple("Transportasi", "transport", "#4B69C6"),
                    Triple("Hiburan", "fun", "#7A5AF8"),
                    Triple("Belanja", "shopping", "#DB4E96"),
                    Triple("Kesehatan", "health", "#2FB673")
                )
                categories.forEach { (name, iconKey, colorHex) ->
                    db.execSQL(
                        "INSERT INTO categories (name, iconKey, colorHex, isDefault) VALUES ('$name', '$iconKey', '$colorHex', 1)"
                    )
                }
            }
        }
    }
}
