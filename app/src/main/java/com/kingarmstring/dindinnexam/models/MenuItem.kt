package com.kingarmstring.dindinnexam.models

data class MenuItem(//maybe there should be an id in real life situation.
    val imgUrl: String = "",
    val name: String = "",
    val desc: String = "",
    val nutritionFacts: String = "",
    val price: Float = 0.0f,
)