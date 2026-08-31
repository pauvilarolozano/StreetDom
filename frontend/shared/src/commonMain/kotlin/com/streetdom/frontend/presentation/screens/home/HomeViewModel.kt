package com.streetdom.frontend.presentation.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.streetdom.frontend.domain.storage.UserStorage
import com.streetdom.frontend.domain.useCase.AuthUseCase
import com.streetdom.frontend.domain.useCase.LocationUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userStorage: UserStorage,
    private val authUseCase: AuthUseCase,
    private val locationUseCase: LocationUseCase

) : ViewModel() {
    
    var uiState by mutableStateOf(HomeUiState())
        private set

    private val _events = MutableSharedFlow<HomeEvent>()
    val events = _events.asSharedFlow()

    init {
        loadUser()
    }

    fun onPlayClick() {
        viewModelScope.launch {
            if (locationUseCase.isLocationEnabled()) {
                _events.emit(HomeEvent.NavigateToPlay)
            } else {
                _events.emit(HomeEvent.LocationDisabled)
            }
        }
    }

    fun onLogoutClick() {
        viewModelScope.launch {
            authUseCase.logout()
            _events.emit(HomeEvent.LogoutSuccess)
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            try {
                val user = userStorage.getCurrentUser()

                uiState = uiState.copy(
                    currentUser = user,
                    isLoading = false
                )
            } catch (_: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                )
            }
        }
    }
}