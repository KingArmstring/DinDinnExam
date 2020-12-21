package com.kingarmstring.dindinnexam.repository

import com.kingarmstring.dindinnexam.models.MenuItem
import com.kingarmstring.dindinnexam.network.DinDinnApi
import com.kingarmstring.dindinnexam.utils.Constants
import com.kingarmstring.dindinnexam.utils.Constants.Companion.BACKEND_FILE_CART
import com.kingarmstring.dindinnexam.utils.Constants.Companion.MENU_ITEM_KEY_ID
import com.kingarmstring.dindinnexam.utils.Constants.Companion.MENU_ITEM_KEY_IMAGE_URL
import com.kingarmstring.dindinnexam.utils.Constants.Companion.MENU_ITEM_KEY_NAME
import com.kingarmstring.dindinnexam.utils.Constants.Companion.MENU_ITEM_KEY_PRICE
import com.kingarmstring.dindinnexam.utils.Constants.Companion.MENU_ITEM_KEY_TYPE
import com.kingarmstring.dindinnexam.utils.Constants.Companion.MENU_ITEM__KEY_DESC
import com.kingarmstring.dindinnexam.utils.Constants.Companion.MENU_ITEM__KEY_NUTRITION_FACTS
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import javax.inject.Inject

class MenuRepository
@Inject constructor(
    val dinDinnApi: DinDinnApi,
    val fileDir: File
) {

//NetworkManager(menuResponse).create()
    fun getPizzas() = dinDinnApi
        .getMenu(Constants.QUERY_NAME_PIZZA)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .flatMap {
            val pizzasList: List<MenuItem> = it.filter { menuItem ->
                menuItem.type == Constants.QUERY_NAME_PIZZA
            }
            return@flatMap Observable.fromIterable(mutableListOf(pizzasList))
        }

//    fun getSushi() = NetworkManager().create()
//        .getMenu("sushi")
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribeOn(Schedulers.io())

//    fun getDrinks() = NetworkManager().create()
//        .getMenu("drinks")
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribeOn(Schedulers.io())

    fun addToCart(menuItem: MenuItem) : Int {
        //read the already existing JSONArray cart from file in form of String.
        val oldCartString = readJSONArrayFile()

        //convert the old JSONArrayString into JSONArray
        val menuJSONArray: JSONArray
        menuJSONArray = if (oldCartString.isNotEmpty()) JSONArray(oldCartString)
        else JSONArray()

        //convert the item into JSONObject.
        val jsonMenuItem = JSONObject()
        jsonMenuItem.put(MENU_ITEM_KEY_ID, menuItem.id)
        jsonMenuItem.put(MENU_ITEM_KEY_IMAGE_URL, menuItem.imgUrl)
        jsonMenuItem.put(MENU_ITEM_KEY_NAME, menuItem.name)
        jsonMenuItem.put(MENU_ITEM__KEY_DESC, menuItem.desc)
        jsonMenuItem.put(MENU_ITEM__KEY_NUTRITION_FACTS, menuItem.nutritionFacts)
        jsonMenuItem.put(MENU_ITEM_KEY_PRICE, menuItem.price)
        jsonMenuItem.put(MENU_ITEM_KEY_TYPE, menuItem.type)

        //add converted JSONObject to the JSONArray
        menuJSONArray.put(jsonMenuItem)
        val sizeOfCart = menuJSONArray.length()

        //add the updated JSONArray to the backend(using local file system to mock the backend)
        val file = File(fileDir, BACKEND_FILE_CART)
        val fileWrite = FileWriter(file)
        val bufferedWriter = BufferedWriter(fileWrite)
        bufferedWriter.write(menuJSONArray.toString())
        bufferedWriter.close()
        return sizeOfCart
    }

    private fun readJSONArrayFile(): String {
        return try {
            val file = File(fileDir, BACKEND_FILE_CART)
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

    fun getCartCount(): Int {
        val oldCartString = readJSONArrayFile()
        val menuJSONArray: JSONArray
        menuJSONArray = if (oldCartString.isNotEmpty()) JSONArray(oldCartString)
        else JSONArray()
        return menuJSONArray.length()
    }
}