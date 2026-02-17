package ru.malevichrp.bbank.features.registration.presentation

sealed interface RegistrationUiEffect {
    data object SuccessRegistration : RegistrationUiEffect
    data class ShowError(val error: String) : RegistrationUiEffect
}