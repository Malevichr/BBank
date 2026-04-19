package ru.malevichrp.bbank.core

import ru.malevichrp.bbank.features.home.domain.HomeDomainError.Mapper

interface DomainError {
    fun <T> map(mapper: Mapper<T>): T
}
