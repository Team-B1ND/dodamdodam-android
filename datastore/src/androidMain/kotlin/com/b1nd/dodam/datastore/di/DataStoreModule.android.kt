package com.b1nd.dodam.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.b1nd.dodam.datastore.createDataStore
import com.b1nd.dodam.datastore.repository.DatastoreRepository
import org.koin.core.module.Module
import org.koin.dsl.module

actual val dataStoreModule: Module = module {
    single<DataStore<Preferences>> {
        val context: Context = get()
        createDataStore(context)
    }

    single<DatastoreRepository> {
        DatastoreRepository(get(), get())
    }
}
