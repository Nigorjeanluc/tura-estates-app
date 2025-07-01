package com.example.turaestates.di

import com.example.turaestates.auth.signin.data.repository.SigninRepositoryImpl
import com.example.turaestates.auth.signin.domain.repository.SigninRepository
import com.example.turaestates.auth.signup.data.repository.SignupRepositoryImpl
import com.example.turaestates.auth.signup.domain.repository.SignupRepository
import com.example.turaestates.properties.data.repository.PropertiesRepositoryImpl
import com.example.turaestates.properties.domain.repository.PropertiesRepository
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

    @Binds
    abstract fun bindSignupRepository(
        impl: SignupRepositoryImpl
    ): SignupRepository

    @Binds
    abstract fun bindPropertiesRepository(
        impl: PropertiesRepositoryImpl
    ): PropertiesRepository
}