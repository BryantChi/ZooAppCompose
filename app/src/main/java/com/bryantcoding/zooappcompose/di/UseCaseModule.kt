package com.bryantcoding.zooappcompose.di

import com.bryantcoding.zooappcompose.data.repository.DataRepository
import com.bryantcoding.zooappcompose.domain.usecase.GetDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetDataUseCase(dataRepository: DataRepository): GetDataUseCase {
        return GetDataUseCase(dataRepository)
    }

}