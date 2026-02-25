package ru.malevichrp.bbank.features.login.presentation

sealed interface LoginUiEffect {
    data class ShowError(val error: String) : LoginUiEffect
    data object SuccessLogin : LoginUiEffect
}