package ru.malevichrp.bbank.features.registration.domain

import kotlinx.coroutines.delay
import ru.malevichrp.bbank.features.registration.presentation.RegistrationData
import javax.inject.Inject

interface RegistrationRepository {
    suspend fun register(
        registrationData: RegistrationData
    ): RegistrationResult

    class Fake @Inject constructor() : RegistrationRepository {
        private var shouldError = true

        override suspend fun register(registrationData: RegistrationData): RegistrationResult {
            delay(2000)
            if (shouldError) {
                shouldError = false
                return RegistrationResult.Error(RegistrationDomainError.CommonError)
            }
            return RegistrationResult.Success
        }
    }
}