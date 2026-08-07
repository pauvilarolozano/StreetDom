package com.streetdom.frontend.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            commonModule,
            iosModule
        )
    }


}