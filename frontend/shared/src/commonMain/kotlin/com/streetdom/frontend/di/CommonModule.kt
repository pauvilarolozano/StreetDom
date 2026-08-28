package com.streetdom.frontend.di

import com.streetdom.frontend.data.config.HttpClientFactory
import com.streetdom.frontend.data.remote.AuthApi
import com.streetdom.frontend.data.remote.KtorAuthApi
import com.streetdom.frontend.data.repository.AuthRepositoryImpl
import com.streetdom.frontend.data.storage.UserStorageImpl
import com.streetdom.frontend.data.storage.TokensStorageImpl
import com.streetdom.frontend.domain.repository.AuthRepository
import com.streetdom.frontend.domain.storage.TokensStorage
import com.streetdom.frontend.domain.storage.UserStorage
import com.streetdom.frontend.domain.useCase.AuthUseCase
import com.streetdom.frontend.domain.useCase.LocationUseCase
import com.streetdom.frontend.presentation.screens.home.HomeViewModel
import com.streetdom.frontend.presentation.screens.login.LoginViewModel
import com.streetdom.frontend.presentation.screens.play.PlayViewModel
import com.streetdom.frontend.presentation.screens.register.RegisterViewModel
import com.streetdom.frontend.presentation.screens.splash.SplashViewModel
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val commonModule = module {

    single <HttpClient> {
        HttpClientFactory(get()).create()
    }

    single <AuthApi> {
        KtorAuthApi(get())
    }

    single<TokensStorage>{
        TokensStorageImpl(get())
    }

    single<UserStorage>{
        UserStorageImpl(get(),get())
    }

    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    single <AuthRepository> {
        AuthRepositoryImpl(get())
    }

    factory <AuthUseCase> {
        AuthUseCase(get(),get(),get())
    }

    factory {
        LocationUseCase(get())
    }

    factory <SplashViewModel> {
        SplashViewModel(get())
    }

    factory <LoginViewModel> {
        LoginViewModel(get())
    }

    factory <RegisterViewModel> {
        RegisterViewModel(get())
    }

    factory <HomeViewModel> {
        HomeViewModel(get(),get())
    }

    factory <PlayViewModel> {
        PlayViewModel(get())
    }

}