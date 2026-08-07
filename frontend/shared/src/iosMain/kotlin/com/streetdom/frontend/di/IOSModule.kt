package com.streetdom.frontend.di

import com.streetdom.frontend.data.storage.IOSSecureStorage
import com.streetdom.frontend.domain.storage.SecureStorage
import org.koin.dsl.module

val iosModule = module {
    single<SecureStorage>{
        IOSSecureStorage()
    }
}