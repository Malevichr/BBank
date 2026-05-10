package ru.malevichrp.bbank.features.operations.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.malevichrp.bbank.features.operations.domain.OperationsRepository

@Module
@InstallIn(ViewModelComponent::class)
interface OperationsModule {
    @Binds
    fun bindOperationsRepository(repository: OperationsRepository.Fake): OperationsRepository
}