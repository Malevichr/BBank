package ru.malevichrp.bbank.features.login.domain

import kotlinx.coroutines.delay
import javax.inject.Inject

interface LoginRepository {
    suspend fun login(login: String, password: String): LoginResult
    class Fake @Inject constructor() : LoginRepository {
        private var shouldError = true
        override suspend fun login(
            login: String,
            password: String
        ): LoginResult {
            delay(2000)
            if (shouldError) {
                shouldError = false
                return LoginResult.Error(LoginDomainError.WrongLoginData)
            }
            return LoginResult.Success
        }
    }
}