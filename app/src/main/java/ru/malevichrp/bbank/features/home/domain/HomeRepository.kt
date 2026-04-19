package ru.malevichrp.bbank.features.home.domain

import kotlinx.coroutines.flow.Flow
import ru.malevichrp.bbank.core.LoadResult

interface HomeRepository<T> {
    fun load(): Flow<LoadResult<T>>
}