package com.kingarmstring.dindinnexam.ui.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kingarmstring.dindinnexam.R
import com.kingarmstring.dindinnexam.models.MenuItem
import com.kingarmstring.dindinnexam.ui.menu.MenuActivity
import kotlinx.android.synthetic.main.activity_payment.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        setFabClickHandler()

        val file = File(filesDir, "cart.json")
        val fileReader = FileReader(file)
        val bufferedReader = BufferedReader(fileReader)
        val stringMenuItems = StringBuilder()
        var line = bufferedReader.readLine()
        while (line != null) {
            stringMenuItems.append(line).append("\n")
            line = bufferedReader.readLine()
        }
        bufferedReader.close()
        val addedMenuItems = stringMenuItems.toString()
        val jsonArray = JSONArray(addedMenuItems)
        val addedItems = mutableListOf<MenuItem>()
        for (i in 0 until jsonArray.length()) {
            val jsonMenuItem = jsonArray[i] as JSONObject
            val menuItem = MenuItem(
                id = jsonMenuItem["id"] as Int,
                imgUrl = jsonMenuItem["imgUrl"] as String,
                name = jsonMenuItem["name"] as String,
                desc = jsonMenuItem["desc"] as String,
                nutritionFacts = jsonMenuItem["nutritionFacts"] as String,
                price = (jsonMenuItem["price"] as Int).toFloat()
            )
            addedItems.add(menuItem)
        }
        val sb = StringBuilder()
        for (i in 0 until addedItems.size) {
            sb.append("${addedItems[i].id} -- ${addedItems[i].name}\n")
        }
        Log.d("KingArmstring", "items: $sb")
        Toast.makeText(this, "$sb", Toast.LENGTH_SHORT).show()
    }

    private fun setFabClickHandler() {
        payment_fab_id.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }
}