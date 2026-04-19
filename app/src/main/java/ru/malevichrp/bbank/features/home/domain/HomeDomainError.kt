package ru.malevichrp.bbank.features.home.domain

import ru.malevichrp.bbank.core.DomainError

interface HomeDomainError : DomainError {
    interface Mapper<T> {
        fun mapCommon(): T
    }

    data object Common : HomeDomainError {
        override fun <T> map(mapper: Mapper<T>): T =
            mapper.mapCommon()
    }
}