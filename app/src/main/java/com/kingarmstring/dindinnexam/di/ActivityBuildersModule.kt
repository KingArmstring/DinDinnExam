package com.kingarmstring.dindinnexam.di

import com.kingarmstring.dindinnexam.di.menu.MenuFragmentsBuildersModule
import com.kingarmstring.dindinnexam.di.menu.MenuModule
import com.kingarmstring.dindinnexam.di.menu.MenuScope
import com.kingarmstring.dindinnexam.di.payments.PaymentModule
import com.kingarmstring.dindinnexam.di.payments.PaymentScope
import com.kingarmstring.dindinnexam.di.payments.PaymentsFragmentsBuildersModule
import com.kingarmstring.dindinnexam.ui.menu.MenuActivity
import com.kingarmstring.dindinnexam.ui.payment.PaymentActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuildersModule {

    @MenuScope
    @ContributesAndroidInjector(modules = [
        MenuModule::class,
        MenuFragmentsBuildersModule::class,
        AppAssistedModule::class,
    ])
    abstract fun contributeMenuActivity() : MenuActivity

    @PaymentScope
    @ContributesAndroidInjector(modules = [
        PaymentModule::class,
        PaymentsFragmentsBuildersModule::class,
        AppAssistedModule::class
    ])
    abstract fun contributePaymentActivity() : PaymentActivity

}