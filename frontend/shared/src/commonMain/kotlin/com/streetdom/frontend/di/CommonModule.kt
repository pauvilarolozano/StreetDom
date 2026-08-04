package com.streetdom.frontend.di

import com.streetdom.frontend.data.remote.AuthApi
import com.streetdom.frontend.data.remote.KtorAuthApi
import com.streetdom.frontend.data.config.createHttpClient
import com.streetdom.frontend.data.repository.AuthRepositoryImpl
import com.streetdom.frontend.data.repository.TokensRepositoryImpl
import com.streetdom.frontend.domain.repository.AuthRepository
import com.streetdom.frontend.domain.repository.TokensRepository
import com.streetdom.frontend.domain.useCase.AuthUseCase
import com.streetdom.frontend.presentation.screens.login.LoginViewModel
import com.streetdom.frontend.presentation.screens.register.RegisterViewModel
import io.ktor.client.HttpClient
import org.koin.dsl.module

val commonModule = module {

    single <HttpClient> {
        createHttpClient()
    }

    single <AuthApi> {
        KtorAuthApi(get())
    }

    single<TokensRepository>{
        TokensRepositoryImpl(get())
    }

    single <AuthRepository> {
        AuthRepositoryImpl(get())
    }

    factory <AuthUseCase> {
        AuthUseCase(get(),get())
    }

    factory <LoginViewModel> {
        LoginViewModel(get())
    }

    factory <RegisterViewModel> {
        RegisterViewModel(get())
    }

}