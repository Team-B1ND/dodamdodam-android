package com.b1nd.dodam.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.b1nd.dodam.datastore.createDataStore
import com.b1nd.dodam.datastore.repository.DataStoreRepository
import com.b1nd.dodam.datastore.repository.DataStoreRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

actual val dataStoreModule: Module = module {
    single<DataStore<Preferences>> {
        createDataStore()
    }

    single<DataStoreRepository> {
        DataStoreRepositoryImpl()
    }
}
