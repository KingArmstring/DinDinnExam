package com.kingarmstring.dindinnexam.utils

import com.kingarmstring.dindinnexam.R

class Constants {

    companion object {

        //I should move all literal constants here and never use them as literals in code, not sure
        //will have time

        /**
         * These are placeholder images, in real application should be brought from urls and loaded
         * into views using Glide for example.
         */
        fun getImage(id: Int) : Int{
            return when(id) {
                0 -> {
                    R.drawable.pizza1
                }
                1 -> {
                    R.drawable.pizza2
                }
                2 -> {
                    R.drawable.pizza3
                }
                3 -> {
                    R.drawable.pizza4
                }
                4 -> {
                    R.drawable.sushi1
                }
                5 -> {
                    R.drawable.sushi2
                }
                6 -> {
                    R.drawable.sushi3
                }
                7 -> {
                    R.drawable.sushi4
                }
                8 -> {
                    R.drawable.drink1
                }
                9 -> {
                    R.drawable.drink2
                }
                10 -> {
                    R.drawable.drink3
                }
                11 -> {
                    R.drawable.drink4
                }

                else -> R.drawable.pizza4
            }
        }

        //Local file representing the backend name:
        const val BACKEND_FILE_MENU = "menu.json"
        const val BACKEND_FILE_CART = "cart.json"
        const val RESPONSE_STRING = "RESPONSE_STRING"
        const val QUERY_NAME_PIZZA = "pizza"

        const val CURVED_MENU_CORNER = 28f
        const val MENU_END_POINT = "menu"
        const val MENU_ITEM_TYPE = "type"
        const val MENU_ITEM_TYPE_PIZZA = "pizza"
        const val MENU_ITEM_TYPE_SUSHI = "sushi"
        const val MENU_ITEM_TYPE_DRINK = "drink"

        //MenuItem keys:
        const val MENU_ITEM_KEY_ID = "id"
        const val MENU_ITEM_KEY_IMAGE_URL = "imgUrl"
        const val MENU_ITEM_KEY_NAME = "name"
        const val MENU_ITEM__KEY_DESC = "desc"
        const val MENU_ITEM__KEY_NUTRITION_FACTS = "nutritionFacts"
        const val MENU_ITEM_KEY_PRICE = "price"
        const val MENU_ITEM_KEY_TYPE = "type"

        //state:
        const val BOTTOM_SHEET_STATE = "BOTTOM_SHEET_STATE"





    }
}