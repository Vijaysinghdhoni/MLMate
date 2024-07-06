package com.vsdhoni5034.mlmate.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.vsdhoni5034.mlmate.data.AuthenticationRepositoryImpl
import com.vsdhoni5034.mlmate.data.UserPreferences
import com.vsdhoni5034.mlmate.domain.AuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun providesFireBaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesAuthenticationRepo(auth: FirebaseAuth): AuthenticationRepository =
        AuthenticationRepositoryImpl(auth)

    @Provides
    @Singleton
    fun providesUserPerf(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context, "Settings")
    }

}