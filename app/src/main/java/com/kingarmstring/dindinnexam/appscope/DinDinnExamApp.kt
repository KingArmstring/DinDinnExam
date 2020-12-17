package com.kingarmstring.dindinnexam.appscope

import android.app.Application
import com.kingarmstring.dindinnexam.di.DaggerAppComponent
import com.kingarmstring.dindinnexam.repository.MenuRepository
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder

class DinDinnExamApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .also {
                it.inject(this)
            }
    }
}