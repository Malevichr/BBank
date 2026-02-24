package ru.malevichrp.bbank.features.home.domain

import kotlinx.coroutines.flow.Flow

interface HomeRepository<T> {
    fun load(): Flow<HomeResult<T>>
}

sealed interface HomeResult<out T> {
    data class Success<out T>(val data: T) : HomeResult<T>
    data class Error(val domainError: HomeDomainError) : HomeResult<Nothing>
}