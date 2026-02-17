package ru.malevichrp.bbank.features.registration.domain

sealed interface RegistrationDomainError {
    fun <T> map(mapper: Mapper<T>): T
    interface Mapper<T> {
        fun mapCommonError(): T
    }

    data object CommonError : RegistrationDomainError {
        override fun <T> map(mapper: Mapper<T>): T =
            mapper.mapCommonError()
    }
}
