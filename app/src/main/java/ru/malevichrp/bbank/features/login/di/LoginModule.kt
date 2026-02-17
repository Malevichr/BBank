package ru.malevichrp.bbank.features.login.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.malevichrp.bbank.features.login.data.LoginRepository

@Module
@InstallIn(ViewModelComponent::class)
interface LoginModule {
    @Binds
    fun bindLoginRepository(repository: LoginRepository.Fake): LoginRepository
}