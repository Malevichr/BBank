package ru.malevichrp.bbank.features.account.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.malevichrp.bbank.features.account.domain.AccountRepository

@Module
@InstallIn(ViewModelComponent::class)
interface AccountModuleModule {
    @Binds
    fun bindAccountRepository(
        repository: AccountRepository.Fake
    ): AccountRepository
}