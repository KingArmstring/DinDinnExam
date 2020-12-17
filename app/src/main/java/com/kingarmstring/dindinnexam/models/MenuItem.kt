package com.kingarmstring.dindinnexam.models

data class MenuItem(//maybe there should be an id in real life situation.
    val id: Int = 0,
    val imgUrl: String = "",
    val name: String = "",
    val desc: String = "",
    val nutritionFacts: String = "",
    val price: Float = 0.0f,
) {

    override fun equals(other: Any?): Boolean {
        try {
            return name == (other as MenuItem).name
        }catch (e: ClassCastException) {
            throw ClassCastException("otherMenuItem must be of type MenuItem")
        }
    }
}