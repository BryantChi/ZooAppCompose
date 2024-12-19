package com.bryantcoding.zooappcompose.di

import com.bryantcoding.zooappcompose.data.local.db.ZooDao
import com.bryantcoding.zooappcompose.data.remote.ApiService
import com.bryantcoding.zooappcompose.data.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataRepository(apiService: ApiService, zooDao: ZooDao): DataRepository {
        return DataRepository(apiService, zooDao)
    }

}