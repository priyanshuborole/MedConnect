package com.priyanshudev.common.di

import android.content.Context
import com.priyanshudev.common.data.datasource.MedConnectDataStoreImpl
import com.priyanshudev.common.domain.repository.MedConnectDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {
    @Provides
    @Singleton
    fun provideMedConnectDataStore(
        @ApplicationContext context: Context
    ): MedConnectDataStore {
        return MedConnectDataStoreImpl(context)
    }

}