package com.example.androidtask.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.androidtask.common.AppDrawables
import com.example.androidtask.data.remote.IApiService
import com.example.androidtask.data.repositories.MainRepositoryImpl
import com.example.androidtask.domain.repositories.IMainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FurnitureModule {


    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO


    @Singleton
    @Provides
    fun provideApplicationContext(
        @ApplicationContext context: Context,
    ) = context
    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context,
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(AppDrawables.ic_image)
            .error(AppDrawables.ic_image)
            .diskCacheStrategy(DiskCacheStrategy.DATA))

    /*
    *repositories
    * */

    @Singleton
    @Provides
    fun provideMainRepository(
        ioDispatcher: CoroutineDispatcher,
        IApiService: IApiService
    ): IMainRepository = MainRepositoryImpl(ioDispatcher, IApiService)
}