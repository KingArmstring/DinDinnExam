package com.kingarmstring.dindinnexam.ui.menu


import android.content.Context
import android.util.Log
import com.airbnb.mvrx.*
import com.kingarmstring.dindinnexam.appscope.DinDinnExamApp
import com.kingarmstring.dindinnexam.di.menu.MenuScope
import com.kingarmstring.dindinnexam.models.MenuItem
import com.kingarmstring.dindinnexam.repository.MenuRepository
import com.kingarmstring.dindinnexam.ui.menu.fragments.PizzaFragment
import com.kingarmstring.dindinnexam.ui.menu.state.MenuState
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import javax.inject.Singleton

class MenuViewModel @AssistedInject constructor(
    @Assisted state: MenuState,
    private val menuRepository: MenuRepository,
    context: Context
) : BaseMvRxViewModel<MenuState>(state, debugMode = true){

    init {
        val cartCount = menuRepository.getCartCount(context)
        setState {
            copy(pizzas = Loading(), cartCount = Success(cartCount))
        }
    }

    fun getPizzas() {
        menuRepository.getPizzas()
            .execute { pizzasState ->
                copy(pizzas = pizzasState)
            }
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
            var recyclerViewIndex = 0
            withState {
                pizzas = it.pizzas.invoke()!!.toMutableList()
                recyclerViewIndex = it.recyclerViewIndex.invoke()?: 0
                setState {
                    copy(pizzas = Success(pizzas),
                        cartCount = Success(newCount),
                        recyclerViewIndex = Success(recyclerViewIndex))
                }
            }
        }
    }

    fun setRecyclerViewIndex(index: Int) {
        var pizzas: List<MenuItem>
        var cartCount = 0
        withState {
            it.pizzas.invoke()?.let { pizzasList ->
                pizzas = pizzasList.toMutableList()
                cartCount = it.cartCount.invoke() ?: 0
                setState {
                    copy(
                        pizzas = Success(pizzas),
                        cartCount = Success(cartCount),
                        recyclerViewIndex = Success(index)
                    )
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