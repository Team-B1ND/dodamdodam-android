package com.seugi.network.bundleidinfo.datasource

interface BundleIdInfoDataSource {
    suspend fun getBundleId(): String?
}
