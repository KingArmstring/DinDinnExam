package com.kingarmstring.dindinnexam.di.menu

import com.kingarmstring.dindinnexam.ui.menu.fragments.PizzaFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MenuFragmentsBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributePizzaFragment() : PizzaFragment
}