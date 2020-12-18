package com.kingarmstring.dindinnexam.di

import android.app.Application
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.kingarmstring.dindinnexam.R
import com.kingarmstring.dindinnexam.di.menu.MenuScope
import com.kingarmstring.dindinnexam.utils.Constants
import dagger.Module
import dagger.Provides
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRequestManager(context: Context, requestOptions: RequestOptions) =
        Glide.with(context).setDefaultRequestOptions(requestOptions)

    @Singleton
    @Provides
    fun provideRequestOptions() = RequestOptions
        .placeholderOf(R.drawable.default_image)
        .error(R.drawable.default_image)
        .transform(
            FitCenter(),
            GranularRoundedCorners(
                Constants.CURVED_MENU_CORNER,
                Constants.CURVED_MENU_CORNER,
                0f,
                0f
            )
        )
}