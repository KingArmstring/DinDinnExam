package com.kingarmstring.dindinnexam.models

data class MenuItem(//maybe there should be an id in real life situation.
    var id: Int = 0,
    val imgUrl: String = "",
    val name: String = "",
    val desc: String = "",
    val nutritionFacts: String = "",
    val price: Float = 0.0f,
    val type: String = ""
) {

    //why do I need to override equals? because in real application we use AsyncListDiffer or
    //ListAdapter, then we need to extend DiffUtil.ItemCallback and here comes the need to well
    //written equals method that represents correctly from logical overview when two objects are
    //equal, unfortunately no actual source of data, no auto-generating for ids, no accurate names
    //so equals here is a little bit ineffective but in real life app, correct implementation of
    //equals is indispensable.
    override fun equals(other: Any?): Boolean {
        try {
            return id == (other as MenuItem).id && name == other.name
        }catch (e: ClassCastException) {
            throw ClassCastException("otherMenuItem must be of type MenuItem")
        }
    }

    override fun toString(): String {
        return """
            id: $id
            imgUrl: $imgUrl
            name: $name
            desc: $desc
            nutritionFacts: $nutritionFacts
            price: $price
            type: $type
        """.trimIndent()
    }
}
