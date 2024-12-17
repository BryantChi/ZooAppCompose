package com.bryantcoding.zooappcompose.di

import android.content.Context
import com.bryantcoding.zooappcompose.data.local.db.ZooDao
import com.bryantcoding.zooappcompose.data.local.db.ZooDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ZooDatabase =
        ZooDatabase.getDatabase(context)

    @Provides
    fun provideDao(database: ZooDatabase): ZooDao = database.zooDao()


}