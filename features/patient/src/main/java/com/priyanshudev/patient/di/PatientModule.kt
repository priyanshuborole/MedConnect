package com.priyanshudev.patient.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.priyanshudev.patient.data.firebase.PatientFirebaseDataSource
import com.priyanshudev.patient.data.firebase.PatientFirebaseRepositoryImpl
import com.priyanshudev.patient.domain.repository.PatientFirebaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PatientModule {

//    @Singleton
//    @Provides
//    fun provideFirebaseAuth(): FirebaseAuth {
//        return Firebase.auth
//    }
//
//    @Singleton
//    @Provides
//    fun provideFirebaseFireStore(): FirebaseFirestore {
//        return Firebase.firestore
//    }

    @Singleton
    @Provides
    fun providePatientFirebaseRepository(
        firebaseDataSource: PatientFirebaseDataSource
    ): PatientFirebaseRepository {
        return PatientFirebaseRepositoryImpl(firebaseDataSource)
    }

    fun providePatientFirebaseDataSource(
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): PatientFirebaseDataSource {
        return PatientFirebaseDataSource(
            firestore,
            firebaseAuth
        )
    }

}