package com.b1nd.dodam.database.core

import app.cash.sqldelight.db.SqlDriver

class MealDatabaseService {
    fun getMealOfMonth(year: Int, month: Int, driver: SqlDriver) {
        val database = DataBase(driver)
        val playerQueries: CoreQuert = database.playerQueries
    }
}