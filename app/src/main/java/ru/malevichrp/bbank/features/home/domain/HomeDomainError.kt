package ru.malevichrp.bbank.features.home.domain

interface HomeDomainError {
    fun <T> map(mapper: Mapper<T>): T
    interface Mapper<T> {
        fun mapCommon(): T
    }

    data object Common : HomeDomainError {
        override fun <T> map(mapper: Mapper<T>): T =
            mapper.mapCommon()
    }
}