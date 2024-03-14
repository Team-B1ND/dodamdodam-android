package com.b1nd.dodam.database.core

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.b1nd.dodam.DodamDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDriver(
        @ApplicationContext context: Context
    ): SqlDriver {
        return AndroidSqliteDriver(
            schema = DodamDatabase.Schema,
            context = context,
            name = "DodamDatabase.db"
        )
    }
}
