package com.priyanshudev.doctor.di

import com.priyanshudev.doctor.data.firebase.DoctorFirebaseDataSource
import com.priyanshudev.doctor.data.firebase.DoctorFirebaseRepositoryImpl
import com.priyanshudev.doctor.domain.repository.DoctorFirebaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DoctorModule {

    @Singleton
    @Provides
    fun provideDoctorFirebaseRepository(
        firebaseDataSource: DoctorFirebaseDataSource
    ): DoctorFirebaseRepository {
        return DoctorFirebaseRepositoryImpl(firebaseDataSource)
    }
}