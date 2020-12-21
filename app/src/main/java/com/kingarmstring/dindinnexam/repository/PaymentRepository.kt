package com.kingarmstring.dindinnexam.repository

import com.kingarmstring.dindinnexam.models.MenuItem
import com.kingarmstring.dindinnexam.utils.Constants.Companion.BACKEND_FILE_CART
import com.kingarmstring.dindinnexam.utils.Constants.Companion.MENU_ITEM_KEY_ID
import com.kingarmstring.dindinnexam.utils.Constants.Companion.MENU_ITEM_KEY_IMAGE_URL
import com.kingarmstring.dindinnexam.utils.Constants.Companion.MENU_ITEM_KEY_NAME
import com.kingarmstring.dindinnexam.utils.Constants.Companion.MENU_ITEM_KEY_PRICE
import com.kingarmstring.dindinnexam.utils.Constants.Companion.MENU_ITEM_KEY_TYPE
import com.kingarmstring.dindinnexam.utils.Constants.Companion.MENU_ITEM__KEY_DESC
import com.kingarmstring.dindinnexam.utils.Constants.Companion.MENU_ITEM__KEY_NUTRITION_FACTS
import io.reactivex.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import javax.inject.Inject

class PaymentRepository
@Inject constructor(val filesDir: File) {

    fun getCartItems(): Single<List<MenuItem>> {
        val addedItems: MutableList<MenuItem> = mutableListOf()
        if (readCartJSONArrayFile().isNotEmpty()) {
            val jsonArray = JSONArray(readCartJSONArrayFile())
            for (i in 0 until jsonArray.length()) {
                val jsonMenuItem = jsonArray[i] as JSONObject
                val menuItem = MenuItem(
                    id = jsonMenuItem[MENU_ITEM_KEY_ID] as Int,
                    imgUrl = jsonMenuItem[MENU_ITEM_KEY_IMAGE_URL] as String,
                    name = jsonMenuItem[MENU_ITEM_KEY_NAME] as String,
                    desc = jsonMenuItem[MENU_ITEM__KEY_DESC] as String,
                    nutritionFacts = jsonMenuItem[MENU_ITEM__KEY_NUTRITION_FACTS] as String,
                    price = (jsonMenuItem[MENU_ITEM_KEY_PRICE] as Int).toFloat(),
                    type = jsonMenuItem[MENU_ITEM_KEY_TYPE] as String
                )
                addedItems.add(menuItem)
            }
        }

        return Single.create { it.onSuccess(addedItems) }
    }

    fun removeItemFromCart(index: Int): Single<List<MenuItem>> {
        //read the already existing JSONArray cart from file in form of String.
        val oldCartString = readCartJSONArrayFile()

        //convert the old JSONArrayString into JSONArray
        val menuJSONArray: JSONArray
        menuJSONArray = if (oldCartString.isNotEmpty()) JSONArray(oldCartString)
        else JSONArray()

        //remove menuItem from the JSONArray
        menuJSONArray.remove(index)
        //add the updated JSONArray to the backend(using local file system to mock the backend)
        val file = File(filesDir, BACKEND_FILE_CART)
        val fileWrite = FileWriter(file)
        val bufferedWriter = BufferedWriter(fileWrite)
        bufferedWriter.write(menuJSONArray.toString())
        bufferedWriter.close()
        return getCartItems()
    }

    private fun readCartJSONArrayFile(): String {
        return try {
            val file = File(filesDir, BACKEND_FILE_CART)
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