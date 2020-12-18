package com.kingarmstring.dindinnexam.di

import android.app.Application
import android.content.Context
import com.kingarmstring.dindinnexam.appscope.DinDinnExamApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityBuildersModule::class,
        AppModule::class,
        AppAssistedModule::class
    ]
)
interface AppComponent: AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(applicationContext: Context) : Builder

        fun build() : AppComponent

    }

    fun inject(application: DinDinnExamApp)

    override fun inject(instance: DaggerApplication?)
}