package com.example.yinyang.di

import com.example.yinyang.network.client
import com.example.yinyang.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSupabaseClient() : SupabaseClient {
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

    @Provides
    @Singleton
    fun provideCartRepository(client: SupabaseClient): CartRepository {
        return CartRepository(client)
    }
}