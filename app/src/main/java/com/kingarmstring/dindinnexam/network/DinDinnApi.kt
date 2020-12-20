package com.kingarmstring.dindinnexam.network


import com.kingarmstring.dindinnexam.models.MenuItem
import com.kingarmstring.dindinnexam.utils.Constants
import com.kingarmstring.dindinnexam.utils.Constants.Companion.MENU_END_POINT
import com.kingarmstring.dindinnexam.utils.Constants.Companion.MENU_ITEM_TYPE
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DinDinnApi {

    /**
     * In real life application, I will WRAP the List<MenuItem> inside a GenericApiResponse, I will
     * include such a file to take a look on it and make sure I use it, but in such a small fake api
     * no need for using it. NOTE this class I am talking about is copied from a google arch comp sample
     * and not made by myself, although I can make it and it's easy, also copying from google samples
     * is not a shame :D, please find this file in same directory this of this file (DinDinnApi file)
     *
     *  // this simple request does not need authorization (like a token) because if I am a
     *  restaurant owner, I would want everyone to be able to see the menu, asking people to create
     *  accounts for that might push them to just leave the app and even worse uninstall it, but in
     *  general in other situation we would want to add authorization field and allow users with
     *  valid tokens only to access the application content.
     */
    @GET(MENU_END_POINT)
    fun getMenu(@Query(MENU_ITEM_TYPE) type: String) : Observable<List<MenuItem>>


}