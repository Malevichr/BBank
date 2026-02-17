package ru.malevichrp.bbank.features.registration.domain

sealed interface RegistrationResult {
    data object Success : RegistrationResult
    data class Error(val error: RegistrationDomainError) : RegistrationResult
}
