package ru.malevichrp.bbank.features.login.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.malevichrp.bbank.features.login.domain.LoginRepository
import ru.malevichrp.bbank.features.login.domain.LoginResult
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: LoginRepository,
    private val errorMapper: LoginErrorMapper
) : ViewModel() {
    val state: StateFlow<LoginUiState> =
        savedStateHandle.getStateFlow(KEY, LoginUiState.Initial)

    private val _uiEffect = MutableSharedFlow<LoginUiEffect>(
        extraBufferCapacity = 1
    )
    val uiEffect = _uiEffect.asSharedFlow()

    fun login(login: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            savedStateHandle[KEY] = LoginUiState.Loading
            val result = repository.login(login, password)
            withContext(Dispatchers.Main) {
                when (result) {
                    is LoginResult.Success -> _uiEffect.tryEmit(LoginUiEffect.SuccessLogin)
                    is LoginResult.Error -> {
                        savedStateHandle[KEY] = LoginUiState.Initial
                        _uiEffect.tryEmit(
                            LoginUiEffect.ShowError(result.exception.map(errorMapper))
                        )
                    }
                }
            }
        }
    }

    companion object {
        private const val KEY = "loginUiState"
    }
}