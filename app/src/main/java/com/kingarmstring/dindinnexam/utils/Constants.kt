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

    }
}