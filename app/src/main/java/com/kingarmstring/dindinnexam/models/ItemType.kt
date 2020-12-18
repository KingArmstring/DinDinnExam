package com.kingarmstring.dindinnexam.models

/**
 * In real application I would use a sealed class instead of just MenuItem.type
 */


sealed class ItemType {

    object Pizza : ItemType()

    object Sushi : ItemType()

    object Drink : ItemType()
}