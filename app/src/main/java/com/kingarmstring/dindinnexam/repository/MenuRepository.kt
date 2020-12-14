package com.kingarmstring.dindinnexam.repository

import com.kingarmstring.dindinnexam.network.NetworkManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MenuRepository {

    fun getPizzas() = NetworkManager.create()
        .getMenu("pizza")
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())

    fun getSushi() = NetworkManager.create()
        .getMenu("sushi")
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())

    fun getDrinks() = NetworkManager.create()
        .getMenu("drinks")
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())


}