package ru.malevichrp.bbank.features.registration.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.malevichrp.bbank.features.registration.domain.RegistrationRepository
import ru.malevichrp.bbank.features.registration.domain.RegistrationResult
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: RegistrationRepository,
    private val mapper: RegistrationErrorMapper
) : ViewModel() {
    val state: StateFlow<RegistrationUiState> =
        savedStateHandle.getStateFlow(KEY, RegistrationUiState.Initial)

    private val _uiEffect = MutableSharedFlow<RegistrationUiEffect>(extraBufferCapacity = 1)
    val uiEffect: SharedFlow<RegistrationUiEffect> = _uiEffect.asSharedFlow()

    fun register(registrationData: RegistrationData) {
        viewModelScope.launch(Dispatchers.IO) {
            savedStateHandle[KEY] = RegistrationUiState.Loading
            val result = repository.register(registrationData)
            withContext(Dispatchers.Main) {
                when (result) {
                    is RegistrationResult.Success ->
                        _uiEffect.tryEmit(RegistrationUiEffect.SuccessRegistration)

                    is RegistrationResult.Error -> {
                        savedStateHandle[KEY] = RegistrationUiState.Initial
                        _uiEffect.tryEmit(
                            RegistrationUiEffect.ShowError(result.error.map(mapper))
                        )
                    }
                }
            }
        }
    }

    companion object {
        private const val KEY = "registrationUiState"
    }
}
