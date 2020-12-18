package com.kingarmstring.dindinnexam.repository

import android.content.Context
import android.util.Log
import com.kingarmstring.dindinnexam.models.MenuItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import javax.inject.Inject
import javax.inject.Named

class PaymentRepository @Inject constructor() {

    fun getCartItems(context: Context) : Observable<List<MenuItem>> {
        val addedItems: MutableList<MenuItem> = mutableListOf()
        if (readCartJSONArrayFile(context).isNotEmpty()) {
            val jsonArray = JSONArray(readCartJSONArrayFile(context))
            for (i in 0 until jsonArray.length()) {
                val jsonMenuItem = jsonArray[i] as JSONObject
                val menuItem = MenuItem(
                    id = jsonMenuItem["id"] as Int,
                    imgUrl = jsonMenuItem["imgUrl"] as String,
                    name = jsonMenuItem["name"] as String,
                    desc = jsonMenuItem["desc"] as String,
                    nutritionFacts = jsonMenuItem["nutritionFacts"] as String,
                    price = (jsonMenuItem["price"] as Int).toFloat(),
                    type = jsonMenuItem["type"] as String
                )
                addedItems.add(menuItem)
            }
        }

        return Observable.fromCallable {
            Thread.sleep(500)//fake delay
            addedItems.toList() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun removeItemFromCart(index: Int, context: Context)  {
        //read the already existing JSONArray cart from file in form of String.
        val oldCartString = readCartJSONArrayFile(context)

        //convert the old JSONArrayString into JSONArray
        val menuJSONArray: JSONArray
        menuJSONArray = if (oldCartString.isNotEmpty()) JSONArray(oldCartString)
        else JSONArray()

        //remove menuItem from the JSONArray
        menuJSONArray.remove(index)
        Log.d("KingArmstring", "removeItemFromCart api: ${menuJSONArray.length()}")

        //add the updated JSONArray to the backend(using local file system to mock the backend)
        val file = File(context.filesDir, "cart.json")
        val fileWrite = FileWriter(file)
        val bufferedWriter = BufferedWriter(fileWrite)
        bufferedWriter.write(menuJSONArray.toString())
        bufferedWriter.close()

    }

    private fun readCartJSONArrayFile(context: Context): String {
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
}