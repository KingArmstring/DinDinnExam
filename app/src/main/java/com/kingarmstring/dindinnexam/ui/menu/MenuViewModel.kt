package com.kingarmstring.dindinnexam.ui.menu


import android.content.Context
import android.util.Log
import com.airbnb.mvrx.*
import com.kingarmstring.dindinnexam.appscope.DinDinnExamApp
import com.kingarmstring.dindinnexam.models.MenuItem
import com.kingarmstring.dindinnexam.repository.MenuRepository
import com.kingarmstring.dindinnexam.ui.menu.fragments.PizzaFragment
import com.kingarmstring.dindinnexam.ui.menu.state.MenuState
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class MenuViewModel @AssistedInject constructor(
    @Assisted state: MenuState,
    private val menuRepository: MenuRepository,
    context: Context
) : BaseMvRxViewModel<MenuState>(state, debugMode = true){

    init {
        val cartCount = menuRepository.getCartCount(context)
        Log.d("KingArmstring", "cartCount: $cartCount")
        setState {
            copy(pizzas = Loading(), cartCount = Success(cartCount))
        }
    }

    fun getPizzas(strResponse: String) = menuRepository.getPizzas(strResponse)
            .execute { pizzasState ->
                copy(pizzas = pizzasState)
            }

//    fun getSushi() = menuRepository.getSushi()
//        .execute { pizzasState ->
//            copy(pizzas = pizzasState)
//        }
//
//    fun getDrinks() = menuRepository.getDrinks()
//        .execute { pizzasState ->
//            copy(pizzas = pizzasState)
//        }

//    fun getMenuItems() = menuRepository.getMenuItems()
//        .execute { pizzasState ->
//            copy(addedMenuItems = pizzasState)
//        }

    fun addItemToCart(menuItem: MenuItem, context: Context) {
        menuRepository.addToCart(menuItem, context).let { newCount ->
            var pizzas: List<MenuItem>
            withState {
                pizzas = it.pizzas.invoke()!!.toMutableList()
                setState {
                    copy(pizzas = Success(pizzas), cartCount = Success(newCount))
                }
            }
        }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(state: MenuState): MenuViewModel
    }

    companion object : MvRxViewModelFactory<MenuViewModel, MenuState> {
        override fun create(viewModelContext: ViewModelContext,
                            state: MenuState): MenuViewModel {
            return (viewModelContext.activity as MenuActivity).viewModelFactory.create(state)
        }
    }
}
//1. finish the add items to an external json file.
//2. add dagger which will help you fix lot's of problems like injecting the context AND don't forget stop calling getPizzas in method onviewcraeted
//3. fix all dirty work, clean code, collect strings in one file.
//4. finish all remaining screen.
//5. check process death.