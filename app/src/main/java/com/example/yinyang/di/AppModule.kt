package com.example.yinyang.di

import com.example.yinyang.repository.AddressRepository
import com.example.yinyang.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSupabaseClient() : SupabaseClient {
        val client = createSupabaseClient(
            supabaseUrl = "https://liskfjzxdlaenoukvmer.supabase.co",
            supabaseKey =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imxpc2tmanp" +
                    "4ZGxhZW5vdWt2bWVyIiwicm9sZSI6ImFub24iLCJpYXQiOjE2ODA5NDE2NzgsImV4cCI6MTk5NjUxNzY" +
                    "3OH0.0QqDcjanSHr4T3Rtk2APYryyGkgDlkkQRs5xCn18bcI"
        ) {
            install(Postgrest) {
                // settings
            }

            install(GoTrue) {
                // settings
            }
        }
        return client
    }

    @Provides
    @Singleton
    fun provideUserRepository(client: SupabaseClient): UserRepository {
        return UserRepository(client)
    }

    @Provides
    @Singleton
    fun provideAddressRepository(client: SupabaseClient): AddressRepository {
        return AddressRepository(client)
    }
}