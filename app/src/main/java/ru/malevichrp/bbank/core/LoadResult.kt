package ru.malevichrp.bbank.core


sealed interface LoadResult<out T> {
    data class Success<out T>(val data: T) : LoadResult<T>
    data class Error(val domainError: DomainError) : LoadResult<Nothing>
}