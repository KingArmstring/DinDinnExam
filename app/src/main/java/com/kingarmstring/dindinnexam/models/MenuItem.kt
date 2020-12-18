package com.kingarmstring.dindinnexam.models

data class MenuItem(//maybe there should be an id in real life situation.
    var id: Int = 0,
    val imgUrl: String = "",
    val name: String = "",
    val desc: String = "",
    val nutritionFacts: String = "",
    val price: Float = 0.0f,
    val type: String
) {

//    override fun equals(other: Any?): Boolean {
//        try {
//            return id == (other as MenuItem).id && name == other.name
//        }catch (e: ClassCastException) {
//            throw ClassCastException("otherMenuItem must be of type MenuItem")
//        }
//    }
}