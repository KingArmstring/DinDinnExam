package com.kingarmstring.dindinnexam.di.menu

import android.content.Context
import dagger.Module
import dagger.Provides
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader

@Module
class MenuModule {

    @MenuScope
    @Provides
    fun provideResponseString(context: Context) : String {
        val inputStream = context.assets?.open("menu.json")
        val builder = StringBuilder()
        val reader = BufferedReader(InputStreamReader(inputStream))
        var str = reader.readLine()
        while (str != null) {
            builder.append(str)
            str = reader.readLine()
        }
        val jsonArray = JSONArray(builder.toString())
        return jsonArray.toString()
    }

}