package ru.malevichrp.bbank.features.login.data

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
                return LoginResult.Error(DomainError.WrongLoginData)
            }
            return LoginResult.Success
        }
    }
}