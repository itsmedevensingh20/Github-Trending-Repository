package com.devendra.devendra_demo.di

import com.devendra.devendra_demo.retrofit.ApiDataSource
import com.devendra.devendra_demo.retrofit.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAuthApi(
        apiDataSource: ApiDataSource
    ): ApiInterface {
        return apiDataSource.create()
    }
}