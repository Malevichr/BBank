package ru.malevichrp.bbank.features.home.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.malevichrp.bbank.features.home.items.accounts.AccountListRepository
import ru.malevichrp.bbank.features.home.items.operations.SpentMoneyRepository
import ru.malevichrp.bbank.features.home.items.profile.FullNameRepository

@Module
@InstallIn(ViewModelComponent::class)
interface HomeModule {
    @Binds
    fun bindFullNameRepository(repository: FullNameRepository.Fake): FullNameRepository

    @Binds
    fun bindSpentMoneyRepository(repository: SpentMoneyRepository.Fake): SpentMoneyRepository

    @Binds
    fun bindAccountListRepository(repository: AccountListRepository.Fake): AccountListRepository
}