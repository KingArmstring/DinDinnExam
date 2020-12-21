package com.kingarmstring.dindinnexam.di.menu

import android.content.Context
import com.kingarmstring.dindinnexam.network.DinDinnApi
import com.kingarmstring.dindinnexam.network.NetworkManager
import com.kingarmstring.dindinnexam.utils.Constants.Companion.BACKEND_FILE_MENU
import com.kingarmstring.dindinnexam.utils.Constants.Companion.RESPONSE_STRING
import dagger.Module
import dagger.Provides
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Named

@Module
class MenuModule {

    //When injecting strings it's very good practice to use Qualifiers or Named annotation
    //(Named only available in Kotlin) because it's luckily to inject more than one String
    @MenuScope
    @Named(RESPONSE_STRING)
    @Provides
    fun provideResponseString(context: Context) : String {
        val inputStream = context.assets?.open(BACKEND_FILE_MENU)
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

    /**
     * dinDinnApi is needed in only one scope(menu scope)
     */
    @MenuScope
    @Provides
    fun provideDinDinnApi(@Named(RESPONSE_STRING) menuResponse: String) : DinDinnApi=
        NetworkManager(menuResponse).create()


}