package com.kingarmstring.dindinnexam.network


import com.kingarmstring.dindinnexam.models.MenuItem
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DinDinnApi {

    /**
     *  // this simple request does not need authorization (like a token) because if I am a
     *  restaurant owner, I would want everyone to be able to see the menu, asking people to create
     *  accounts for that might push them to just leave the app and even worse uninstall it, but in
     *  general in other situation we would want to add authorization field and allow users with
     *  valid tokens only to access the application content.
     */
    @GET("menu")
    fun getMenu(@Query("type") type: String) : Single<List<MenuItem>>

}