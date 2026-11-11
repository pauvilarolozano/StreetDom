package com.streetdom.frontend.di

import com.streetdom.frontend.data.config.HttpClientFactory
import com.streetdom.frontend.data.remote.AuthApi
import com.streetdom.frontend.data.remote.GameApi
import com.streetdom.frontend.data.remote.KtorAuthApi
import com.streetdom.frontend.data.remote.KtorGameApi
import com.streetdom.frontend.data.repository.AuthRepositoryImpl
import com.streetdom.frontend.data.repository.GameRepositoryImpl
import com.streetdom.frontend.data.storage.TokensStorageImpl
import com.streetdom.frontend.data.storage.UserStorageImpl
import com.streetdom.frontend.domain.manager.GpsTrackerManager
import com.streetdom.frontend.domain.repository.AuthRepository
import com.streetdom.frontend.domain.repository.GameRepository
import com.streetdom.frontend.domain.session.AuthenticatedSessionInitializer
import com.streetdom.frontend.domain.storage.TokensStorage
import com.streetdom.frontend.domain.storage.UserStorage
import com.streetdom.frontend.domain.useCase.AuthUseCase
import com.streetdom.frontend.domain.useCase.GameUseCase
import com.streetdom.frontend.domain.useCase.LocationUseCase
import com.streetdom.frontend.presentation.screens.gameMenu.GameMenuViewModel
import com.streetdom.frontend.presentation.screens.home.HomeViewModel
import com.streetdom.frontend.presentation.screens.login.LoginViewModel
import com.streetdom.frontend.presentation.screens.play.PlayViewModel
import com.streetdom.frontend.presentation.screens.register.RegisterViewModel
import com.streetdom.frontend.presentation.screens.splash.SplashViewModel
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

val commonModule = module {

    single(named("authenticated")) {
        HttpClientFactory(get()).createAuthenticated()
    }

    single(named("unauthenticated")) {
        HttpClientFactory(get()).createUnauthenticated()
    }

    single <AuthApi> {
        KtorAuthApi(
            unauthenticatedClient = get(named("unauthenticated")),
            authenticatedClient = get(named("authenticated"))
        )
    }

    single <GameApi> {
        KtorGameApi(
            authenticatedClient = get(named("authenticated")),
        )
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

    factory {
        AuthenticatedSessionInitializer(get())
    }

    factory <AuthUseCase> {
        AuthUseCase(get(), get(), get(), get())
    }

    factory {
        LocationUseCase(get())
    }

    single {
        GpsTrackerManager(get())
    }

    factory <SplashViewModel> {
        SplashViewModel(get(), get())
    }

    factory <LoginViewModel> {
        LoginViewModel(get(), get())
    }

    factory <RegisterViewModel> {
        RegisterViewModel(get(), get())
    }

    factory <HomeViewModel> {
        HomeViewModel(get(), get(), get())
    }

    factory {
        GameMenuViewModel(get())
    }

    single <GameRepository> {
        GameRepositoryImpl(get())
    }

    factory {
        GameUseCase(get(), get())
    }

    factory <PlayViewModel> {
        PlayViewModel(get(), get(), get())
    }

}
