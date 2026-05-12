package ru.malevichrp.bbank.features.operationdetails.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.malevichrp.bbank.features.operationdetails.domain.OperationDetailsRepository

@Module
@InstallIn(ViewModelComponent::class)
interface OperationDetailsModule {
    @Binds
    fun bindOperationDetailsRepository(repository: OperationDetailsRepository.Fake): OperationDetailsRepository
}