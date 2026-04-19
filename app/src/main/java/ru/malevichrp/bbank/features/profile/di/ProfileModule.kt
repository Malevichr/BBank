package ru.malevichrp.bbank.features.profile.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.malevichrp.bbank.features.profile.data.AvatarRepository

@Module
@InstallIn(ViewModelComponent::class)
interface ProfileModule {
    @Binds
    fun bindAvatarRepository(repository: AvatarRepository.Fake): AvatarRepository
}