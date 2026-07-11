package com.faezolmp.momeapp.presentation.screen.Home

import androidx.lifecycle.ViewModel
import com.faezolmp.momeapp.core.data.Resource
import com.faezolmp.momeapp.core.domain.model.ExampleModel
import com.faezolmp.momeapp.core.domain.usecase.UseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class HomeUiState(
    val isLoading: Boolean = false,
    val data: ExampleModel? = null,
    val error: String? = null
)

class HomeViewModel(private val useCase: UseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    private fun load() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        when (val result = useCase.exampleFunction()) {
            is Resource.Success -> _uiState.update {
                it.copy(isLoading = false, data = result.data)
            }
            is Resource.Loading -> _uiState.update {
                it.copy(isLoading = true, data = result.data)
            }
            is Resource.Error -> _uiState.update {
                it.copy(isLoading = false, error = result.message, data = result.data)
            }
        }
    }
}
