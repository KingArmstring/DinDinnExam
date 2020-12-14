package com.kingarmstring.dindinnexam.appscope

import android.app.Application
import com.kingarmstring.dindinnexam.repository.MenuRepository

class DinDinnExamApp : Application() {
    val watchlistRepository by lazy {
        MenuRepository()
    }
}