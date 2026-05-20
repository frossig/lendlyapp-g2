package com.example.simulacro.di

import com.example.simulacro.BuildConfig
import com.example.simulacro.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Módulo Hilt: le dice a Hilt CÓMO construir cada dependencia relacionada
 * con networking. Cualquier clase puede pedir un `ApiService` por constructor
 * y Hilt va a saber instanciarlo gracias a esto.
 *
 * Cambiar `BASE_URL` por la de la API del parcial.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // OJO: tiene que terminar con "/".
    private const val BASE_URL = "https://example.com/api/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}
