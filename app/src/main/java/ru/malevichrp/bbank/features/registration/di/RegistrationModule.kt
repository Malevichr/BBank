package ru.malevichrp.bbank.features.registration.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.malevichrp.bbank.features.registration.domain.RegistrationRepository

@Module
@InstallIn(ViewModelComponent::class)
interface RegistrationModule {
    @Binds
    fun bindRegistrationRepository(repository: RegistrationRepository.Fake): RegistrationRepository
}