package ru.malevichrp.bbank.features.login.domain

sealed interface LoginResult {
    data object Success : LoginResult
    data class Error(val exception: LoginDomainError) : LoginResult
}
