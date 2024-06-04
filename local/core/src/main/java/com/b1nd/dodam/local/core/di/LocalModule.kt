package com.b1nd.dodam.local.core.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.b1nd.database.TestDatabase
import com.b1nd.database.TestQueries
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): SqlDriver = AndroidSqliteDriver(TestDatabase.Schema, context)

    @Provides
    @Singleton
    fun providesTestDatabase(driver: SqlDriver): TestQueries = TestDatabase(driver).testQueries
}
