package com.kingarmstring.dindinnexam.di

import com.kingarmstring.dindinnexam.di.menu.MenuFragmentsBuildersModule
import com.kingarmstring.dindinnexam.ui.menu.MenuActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [MenuFragmentsBuildersModule::class])
    abstract fun contributeMenuActivity() : MenuActivity

}