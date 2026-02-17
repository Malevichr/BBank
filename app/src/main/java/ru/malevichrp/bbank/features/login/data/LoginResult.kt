package ru.malevichrp.bbank.features.login.data

sealed interface LoginResult {
    data object Success : LoginResult
    data class Error(val exception: DomainError) : LoginResult
}
