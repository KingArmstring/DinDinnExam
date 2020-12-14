package com.kingarmstring.dindinnexam.models

sealed class ItemType {

    object Pizza : ItemType()

    object Sushi : ItemType()

    object Drink : ItemType()
}