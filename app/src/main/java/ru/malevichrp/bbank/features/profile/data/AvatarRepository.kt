package ru.malevichrp.bbank.features.profile.data

import kotlinx.coroutines.flow.Flow
import ru.malevichrp.bbank.core.LoadResult
import ru.malevichrp.bbank.features.profile.presentation.ImageSource

interface AvatarRepository {
    fun load(): Flow<LoadResult<ImageSource>>
}