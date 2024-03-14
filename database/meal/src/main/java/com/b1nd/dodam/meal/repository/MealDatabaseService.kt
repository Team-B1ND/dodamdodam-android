package com.b1nd.dodam.meal.repository

import app.cash.sqldelight.db.SqlDriver

class MealDatabaseService {
    fun getMealOfMonth(year: Int, month: Int, driver: SqlDriver) {
        val database = Database(driver)
        val playerQueries: PlayerQueries = database.playerQueries
    }
}