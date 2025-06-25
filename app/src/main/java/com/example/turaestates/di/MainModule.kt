package com.example.turaestates.di

import android.content.Context
import com.example.turaestates.auth.signin.data.remote.SigninApi
import com.example.turaestates.auth.signup.data.remote.SignupApi
import com.example.turaestates.data.DataStoreRepository
import com.example.turaestates.util.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) = DataStoreRepository(context = context)

    @Provides
    @Singleton
    fun provideSigninApi(): SigninApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SigninApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSignupApi(): SignupApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SignupApi::class.java)
    }
}