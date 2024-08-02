package com.b1nd.dodam.datastore

import android.content.Context

fun createDataStore(context: Context) = createCoreDataStore(
    producePath = {
        context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
    },
)
