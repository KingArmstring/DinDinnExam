package com.kingarmstring.dindinnexam.repository

import android.content.Context
import com.kingarmstring.dindinnexam.models.MenuItem
import com.kingarmstring.dindinnexam.network.NetworkManager
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import javax.inject.Inject

class MenuRepository @Inject constructor() {

//    val

    fun getPizzas(strResponse: String) = NetworkManager(strResponse).create()
        .getMenu("pizza")
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())

//    fun getSushi() = NetworkManager().create()
//        .getMenu("sushi")
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribeOn(Schedulers.io())
//
//    fun getDrinks() = NetworkManager().create()
//        .getMenu("drinks")
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribeOn(Schedulers.io())

    fun addToCart(menuItem: MenuItem, context: Context) : Int {
        //read the already existing JSONArray cart from file in form of String.
        val oldCartString = readJSONArrayFile(context)

        //convert the old JSONArrayString into JSONArray
        val menuJSONArray: JSONArray
        menuJSONArray = if (oldCartString.isNotEmpty()) JSONArray(oldCartString)
        else JSONArray()
        //convert the item into JSONObject.
        val jsonMenuItem = JSONObject()
        jsonMenuItem.put("id", menuItem.id)
        jsonMenuItem.put("imgUrl", menuItem.imgUrl)
        jsonMenuItem.put("name", menuItem.name)
        jsonMenuItem.put("desc", menuItem.desc)
        jsonMenuItem.put("nutritionFacts", menuItem.nutritionFacts)
        jsonMenuItem.put("price", menuItem.price)

        //add converted JSONObject to the JSONArray
        menuJSONArray.put(jsonMenuItem)
        val sizeOfCart = menuJSONArray.length()
        //add the updated JSONArray to the backend(using local file system to mock the backend)
        val file = File(context.filesDir, "cart.json")
        val fileWrite = FileWriter(file)
        val bufferedWriter = BufferedWriter(fileWrite)
        bufferedWriter.write(menuJSONArray.toString())
        bufferedWriter.close()
        return sizeOfCart
    }

    private fun readJSONArrayFile(context: Context): String {
        return try {
            val file = File(context.filesDir, "cart.json")
            val fileReader = FileReader(file)
            val bufferedReader = BufferedReader(fileReader)
            val stringMenuJSONArray = StringBuilder()
            var line = bufferedReader.readLine()
            while (line != null) {
                stringMenuJSONArray.append(line).append("\n")
                line = bufferedReader.readLine()
            }
            bufferedReader.close()
            stringMenuJSONArray.toString()
        }catch (e: FileNotFoundException) {
            ""
        }
    }

    fun getCartCount(context: Context): Int {
        val oldCartString = readJSONArrayFile(context)
        val menuJSONArray: JSONArray
        menuJSONArray = if (oldCartString.isNotEmpty()) JSONArray(oldCartString)
        else JSONArray()
        return menuJSONArray.length()
    }


}