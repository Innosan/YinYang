package com.example.yinyang.di

import com.example.yinyang.repository.AddressRepository
import com.example.yinyang.repository.FavoriteRepository
import com.example.yinyang.repository.ProductRepository
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
            supabaseUrl = "https://yfwjdrfyiqluljyiocqd.supabase.co",
            supabaseKey =
                    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inl" +
                    "md2pkcmZ5aXFsdWxqeWlvY3FkIiwicm9sZSI6ImFub24iLCJpYXQiOjE2ODQ1MjU3NzUsImV4cCI6M" +
                    "jAwMDEwMTc3NX0.DAu-F5_-IhVAtf5LwNG2fWBrY5hT7yD4etRExeHfwyQ"
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

    @Provides
    @Singleton
    fun provideProductRepository(client: SupabaseClient): ProductRepository {
        return ProductRepository(client)
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(client: SupabaseClient): FavoriteRepository {
        return FavoriteRepository(client)
    }
}