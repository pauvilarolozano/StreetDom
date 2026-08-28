package com.streetdom.frontend.presentation.screens.play

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.streetdom.frontend.domain.model.Location
import com.streetdom.frontend.domain.useCase.LocationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlayViewModel(
    private val locationUseCase: LocationUseCase

) : ViewModel() {

    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location.asStateFlow()

    fun loadLocation() {
        viewModelScope.launch {
            _location.value = locationUseCase.getCurrentLocation()
        }
    }

}