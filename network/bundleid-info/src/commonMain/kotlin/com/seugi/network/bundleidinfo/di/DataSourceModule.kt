package com.seugi.network.bundleidinfo.di

import com.seugi.network.bundleidinfo.api.BundleIdInfoService
import com.seugi.network.bundleidinfo.datasource.BundleIdInfoDataSource
import org.koin.dsl.module

val bundleIdInfoDataSourceModule = module {
    single<BundleIdInfoDataSource> {
        BundleIdInfoService(get())
    }
}
