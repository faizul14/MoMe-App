package com.faezolmp.momeapp.presentation.screen.Profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faezolmp.momeapp.core.domain.model.Profile
import com.faezolmp.momeapp.core.domain.usecase.ProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EditProfileUiState(
    val name: String = "",
    val email: String = "",
    val loaded: Boolean = false,
    val saved: Boolean = false
)

class EditProfileViewModel(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

    private var prefilled = false

    init {
        viewModelScope.launch {
            val profile = profileUseCase.get().first()
            if (!prefilled) {
                prefilled = true
                _uiState.update {
                    it.copy(
                        name = profile?.name.orEmpty(),
                        email = profile?.email.orEmpty(),
                        loaded = true
                    )
                }
            }
        }
    }

    fun onNameChange(value: String) {
        _uiState.update { it.copy(name = value) }
    }

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value) }
    }

    fun save() {
        val current = _uiState.value
        if (current.name.isBlank()) return
        viewModelScope.launch {
            profileUseCase.save(Profile(name = current.name.trim(), email = current.email.trim()))
            _uiState.update { it.copy(saved = true) }
        }
    }
}
