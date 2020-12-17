package com.kingarmstring.dindinnexam.appscope

import android.app.Application
import com.kingarmstring.dindinnexam.repository.MenuRepository
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder

class DinDinnExamApp : Application() {
    val menuRepository by lazy {
        MenuRepository()
    }
}