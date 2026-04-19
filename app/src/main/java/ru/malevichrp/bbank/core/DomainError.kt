package ru.malevichrp.bbank.core


interface DomainError {
    fun <T> map(mapper: Mapper<T>): T
    interface Mapper<T> {
        fun mapCommon(): T
        fun mapMessaged(message: String): T
    }

    data object Common : DomainError {
        override fun <T> map(mapper: Mapper<T>): T =
            mapper.mapCommon()
    }

    data class Messaged(private val message: String) : DomainError {
        override fun <T> map(mapper: Mapper<T>): T =
            mapper.mapMessaged(message)
    }
}
