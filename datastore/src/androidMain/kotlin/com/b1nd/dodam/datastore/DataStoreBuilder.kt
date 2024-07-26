package com.b1nd.dodam.datastore

import android.content.Context
import androidx.datastore.core.DataStore

fun createDataStore(context: Context) = createDataStore(
    producePath = {
        context.filesDir.resolve(dataStoreFileName).absolutePath
    }
)