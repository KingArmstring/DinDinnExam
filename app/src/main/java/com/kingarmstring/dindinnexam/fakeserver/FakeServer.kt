package com.kingarmstring.dindinnexam.fakeserver

import androidx.core.content.ContextCompat
import com.kingarmstring.dindinnexam.models.MenuItem
import java.lang.StringBuilder


class FakeServer {

    companion object {
        //This object represents data stored in server, maybe stored in mongodb for example or any
        //other database
        val serverData: List<MenuItem> = listOf(
            MenuItem(
                id = 0,
                imgUrl = "placeholder",
                name = "Pizza 1",
                desc = "chicken, Peperoni, Tomatoes, BBQ sauce",
                nutritionFacts = "190 grams, 40 cm",
                price = 12.0f
            ),MenuItem(
                id = 1,
                imgUrl = "placeholder",
                name = "Pizza 2",
                desc = "chicken, Peperoni, Tomatoes, BBQ sauce",
                nutritionFacts = "190 grams, 40 cm",
                price = 12.0f
            ),MenuItem(
                id = 2,
                imgUrl = "placeholder",
                name = "Pizza 3",
                desc = "chicken, Peperoni, Tomatoes, BBQ sauce",
                nutritionFacts = "190 grams, 40 cm",
                price = 12.0f
            ),MenuItem(
                id = 3,
                imgUrl = "placeholder",
                name = "Pizza 4",
                desc = "chicken, Peperoni, Tomatoes, BBQ sauce",
                nutritionFacts = "190 grams, 40 cm",
                price = 12.0f
            ),
        )
        fun mockPizzaResponse() : String {
            val responseBuilder = StringBuilder()
            responseBuilder.append("[")
            for (i in 0..serverData.lastIndex) {
                responseBuilder.append(menuItemToJson(serverData[i]))
                if (i != serverData.lastIndex) responseBuilder.append(",")
            }
            responseBuilder.append("]")
            return responseBuilder.toString()
        }

        fun mockSushiResponse() : String {
            return """
                    [
                        {
                            "imgUrl": "placeholder",
                            "name": "sushi 1",
                            "desc": "chicken, Peperoni, Tomatoes, BBQ sauce"
                            "nutritionFacts": "190 grams, 40 cm",
                            "price": "12.0f",
                        },
                        {
                            "imgUrl": "placeholder",
                            "name": "sushi 2",
                            "desc": "chicken, Peperoni, Tomatoes, BBQ sauce"
                            "nutritionFacts": "190 grams, 40 cm",
                            "price": "12.0f",
                        },
                        {
                            "imgUrl": "placeholder",
                            "name": "sushi 3",
                            "desc": "chicken, Peperoni, Tomatoes, BBQ sauce"
                            "nutritionFacts": "190 grams, 40 cm",
                            "price": "12.0f",
                        },
                        {
                            "imgUrl": "placeholder",
                            "name": "sushi 4",
                            "desc": "chicken, Peperoni, Tomatoes, BBQ sauce"
                            "nutritionFacts": "190 grams, 40 cm",
                            "price": "12.0f",
                        }
                    ]
            """.trimIndent()
        }

        fun mockDrinksResponse() : String {
            return """
                    [
                        {
                            "imgUrl": "placeholder",
                            "name": "drink 1",
                            "desc": "chicken, Peperoni, Tomatoes, BBQ sauce"
                            "nutritionFacts": "190 grams, 40 cm",
                            "price": "12.0f",
                        },
                        {
                            "imgUrl": "placeholder",
                            "name": "drink 2",
                            "desc": "chicken, Peperoni, Tomatoes, BBQ sauce"
                            "nutritionFacts": "190 grams, 40 cm",
                            "price": "12.0f",
                        },
                        {
                            "imgUrl": "placeholder",
                            "name": "drink 3",
                            "desc": "chicken, Peperoni, Tomatoes, BBQ sauce"
                            "nutritionFacts": "190 grams, 40 cm",
                            "price": "12.0f",
                        },
                        {
                            "imgUrl": "placeholder",
                            "name": "drink 4",
                            "desc": "chicken, Peperoni, Tomatoes, BBQ sauce"
                            "nutritionFacts": "190 grams, 40 cm",
                            "price": "12.0f",
                        }
                    ]
            """.trimIndent()
        }

        private fun menuItemToJson(menuItem: MenuItem) : String =
            """
                {
                    "id": "${menuItem.id}",
                    "imgUrl": "${menuItem.imgUrl}",
                    "name": "${menuItem.name}",
                    "desc": "${menuItem.desc}",
                    "nutritionFacts": "${menuItem.nutritionFacts}",
                    "price": "${menuItem.price}"
                }
            """.trimIndent()

    }
}