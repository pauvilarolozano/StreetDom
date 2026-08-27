package com.streetdom.frontend.di

import com.streetdom.frontend.data.storage.IOSSecureStorage
import com.streetdom.frontend.domain.storage.SecureStorage
import com.streetdom.frontend.presentation.screens.play.map.MapConfig
import org.koin.dsl.module

val iosModule = module {

    single {
        MapConfig(
            mapTilerApiKey = "" //TODO anadir sistema para obtener api key de ... properties?
        )
    }

    single<SecureStorage>{
        IOSSecureStorage()
    }
}