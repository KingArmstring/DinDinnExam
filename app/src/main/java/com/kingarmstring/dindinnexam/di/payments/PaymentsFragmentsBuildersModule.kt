package com.kingarmstring.dindinnexam.di.payments

import com.kingarmstring.dindinnexam.ui.payment.fragments.CartFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PaymentsFragmentsBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributePizzaFragment() : CartFragment
}