package com.b1nd.dodam.keystore.di

import com.b1nd.dodam.keystore.KeyStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KeyStoreModule {
    @Provides
    @Singleton
    fun providesKeyStoreManager(): KeyStoreManager = KeyStoreManager()
}