package com.streetdom.frontend.di

import com.streetdom.frontend.data.remote.AuthApi
import com.streetdom.frontend.data.remote.KtorAuthApi
import com.streetdom.frontend.data.config.createHttpClient
import com.streetdom.frontend.data.repository.DefaultAuthRepository
import com.streetdom.frontend.domain.repository.AuthRepository
import com.streetdom.frontend.domain.useCase.AuthUseCase
import com.streetdom.frontend.presentation.screens.login.LoginViewModel
import com.streetdom.frontend.presentation.screens.register.RegisterViewModel
import io.ktor.client.HttpClient
import org.koin.dsl.module

val appModule = module {

    single <HttpClient> {
        createHttpClient()
    }

    single <AuthApi> {
        KtorAuthApi(get())
    }

    single <AuthRepository> {
        DefaultAuthRepository(get())
    }

    single <AuthUseCase> {
        AuthUseCase(get())
    }

    factory <LoginViewModel> {
        LoginViewModel(get())
    }

    factory <RegisterViewModel> {
        RegisterViewModel(get())
    }

}