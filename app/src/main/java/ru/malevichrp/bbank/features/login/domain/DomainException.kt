package ru.malevichrp.bbank.features.login.domain

sealed interface DomainError {
    fun <T> map(mapper: Mapper<T>): T
    interface Mapper<T> {
        fun mapWrongLoginData(): T
    }

    data object WrongLoginData : DomainError {
        override fun <T> map(mapper: Mapper<T>): T =
            mapper.mapWrongLoginData()
    }
}