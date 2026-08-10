package com.streetdom.frontend.presentation.screens.home

import com.streetdom.frontend.domain.model.User

data class HomeUiState(
    val currentUser: User? = User(0,"Unknown","Unknown@a.com"),
    val coins: Int = 0,
    val isLoading: Boolean = false
)