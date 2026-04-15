package ru.malevichrp.bbank.features.profile.data

import kotlinx.coroutines.flow.Flow
import ru.malevichrp.bbank.features.home.domain.HomeDomainError
import ru.malevichrp.bbank.features.profile.presentation.ImageSource

interface AvatarRepository {
    fun load(): Flow<AvatarResult>
}

sealed interface AvatarResult {
    data class Success(val data: ImageSource) : AvatarResult
    data class Error(val domainError: HomeDomainError) : AvatarResult
}
