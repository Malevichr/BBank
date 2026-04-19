package ru.malevichrp.bbank.features.profile.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.malevichrp.bbank.core.DomainError
import ru.malevichrp.bbank.core.LoadResult
import ru.malevichrp.bbank.features.profile.presentation.ImageSource
import javax.inject.Inject

interface AvatarRepository {
    fun load(): Flow<LoadResult<ImageSource>>
    class Fake @Inject constructor() : AvatarRepository {
        private var shouldError = true
        override fun load(): Flow<LoadResult<ImageSource>> = flow {
            delay(1000)
            if (shouldError) {
                emit(LoadResult.Error(DomainError.Common))
                shouldError = false
            } else {
                emit(LoadResult.Success(ImageSource.Empty))
            }
        }
    }
}