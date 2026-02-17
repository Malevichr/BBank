package ru.malevichrp.bbank.features.login.presentation

sealed interface UiEffect {
    data class ShowError(val error: String) : UiEffect
    data object SuccessLogin : UiEffect
}