package com.b1nd.dodam.data.bundleidinfo.di

import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.data.bundleidinfo.BundleIdInfoRepository
import com.b1nd.dodam.data.bundleidinfo.repository.BundleIdInfoRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val bundleIdInfoRepositoryModule = module {
    single<BundleIdInfoRepository> {
        BundleIdInfoRepositoryImpl(get(), get(named(DispatcherType.IO)))
    }
}
