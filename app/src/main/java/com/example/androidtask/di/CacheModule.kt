package com.example.androidtask.di

import android.content.Context
import android.content.SharedPreferences
import com.example.androidtask.common.Constants.PREFERENCES_NAME
import com.example.androidtask.data.cache.ComplexPreferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CacheModule {


    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideSharedPreferencesEditor(preferences: SharedPreferences): SharedPreferences.Editor =
        preferences.edit()

    @Singleton
    @Provides
    fun provideGson(): Gson =Gson()


    @Singleton
    @Provides
    fun provideComplexPreference(
        GSON: Gson,
        preferences: SharedPreferences,
        editor: SharedPreferences.Editor
    ): ComplexPreferences =
        ComplexPreferences(GSON, preferences, editor)

}