package com.example.currency_converter.data.di

import com.example.currency_converter.data.remote.ApiInterface
import com.example.currency_converter.data.repository.AppRepositoryImpl
import com.example.currency_converter.domain.repositoryInt.AppRepository
import com.example.currency_converter.domain.useCases.GetRateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit : Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideAppRepository(api: ApiInterface) : AppRepository{
        return AppRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideRepositoryToUseCase(repository: AppRepository): GetRateUseCase {
        return GetRateUseCase(repository)
    }
}