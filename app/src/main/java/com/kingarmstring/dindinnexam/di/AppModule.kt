package com.kingarmstring.dindinnexam.di

import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.kingarmstring.dindinnexam.R
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRequestOptions() = RequestOptions
        .placeholderOf(R.drawable.default_image)
        .error(R.drawable.default_image)
        .transform(
            FitCenter(),
            GranularRoundedCorners(
                28f,
                28f,
                0f,
                0f
            )
        )
}