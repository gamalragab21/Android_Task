package com.example.androidtask.di


import com.example.androidtask.common.Constants
import com.example.androidtask.common.Constants.BASE_URL
import com.example.androidtask.data.cache.ComplexPreferences
import com.example.androidtask.data.remote.IApiService
import com.example.androidtask.domain.modules.User
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.HEADERS
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        complexPreferences: ComplexPreferences
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val original: Request = chain.request()
                val builder: Request.Builder =original.newBuilder()
                val userData=complexPreferences.getObject(Constants.USER_DATA, User::class.java)
                val lang=complexPreferences.getString(Constants.USER_LANG,"en")

                val newRequest = builder.apply {
                    addHeader("lang", lang)
                    userData?.also {
                        addHeader("Accept", "application/json")
                        addHeader("Authorization", it.email) // here must change email to token
                        build()
                    }
                }
                return@addInterceptor chain.proceed(newRequest.build())
            }
            .build()

    }

    @Singleton
    @Provides
    fun provideGsonConverter(): GsonConverterFactory {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideFurnitureServiceApi(
        retrofit: Retrofit
    ): IApiService {
        return retrofit.create(IApiService::class.java)
    }


}