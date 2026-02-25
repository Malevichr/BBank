package ru.malevichrp.bbank.features.login.domain

sealed interface LoginDomainError {
    fun <T> map(mapper: Mapper<T>): T
    interface Mapper<T> {
        fun mapWrongLoginData(): T
    }

    data object WrongLoginData : LoginDomainError {
        override fun <T> map(mapper: Mapper<T>): T =
            mapper.mapWrongLoginData()
    }
}