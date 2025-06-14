package com.example.turaestates.di

import com.example.turaestates.auth.signin.data.repository.SigninRepositoryImpl
import com.example.turaestates.auth.signin.domain.repository.SigninRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {

    @Binds
    abstract fun bindSigninRepository(
        impl: SigninRepositoryImpl
    ): SigninRepository
}