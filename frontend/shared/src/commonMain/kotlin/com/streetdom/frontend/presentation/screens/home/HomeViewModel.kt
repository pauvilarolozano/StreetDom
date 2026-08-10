package com.streetdom.frontend.presentation.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.streetdom.frontend.domain.repository.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository

) : ViewModel() {
    
    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        loadUser()
    }

    fun onPlayClick() {
        // Lógica para iniciar el juego
    }

    private fun loadUser() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            try {
                val user = userRepository.getCurrentUser()

                uiState = uiState.copy(
                    currentUser = user,
                    isLoading = false
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    //error = e.message
                )
            }
        }
    }
}