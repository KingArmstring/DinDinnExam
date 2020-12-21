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
) : BaseMvRxViewModel<MenuState>(state, debugMode = true){

    init {
        val cartCount = menuRepository.getCartCount()
        setState {
            copy(pizzas = Loading(), cartCount = Success(cartCount))
        }
    }

    fun firePizzasEvent() {
        menuRepository.getPizzas()
            .execute { pizzasState ->
                copy(pizzas = pizzasState, cartCount = Success(menuRepository.getCartCount()))
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

    fun addItemToCart(menuItem: MenuItem) {
        menuRepository.addToCart(menuItem).let { newCount ->
            var pizzas: List<MenuItem>
            var recyclerViewIndex = 0
            withState {
                pizzas = it.pizzas.invoke()?.toMutableList() ?: mutableListOf()
                recyclerViewIndex = it.recyclerViewIndex.invoke()?: 0
                setState {
                    copy(pizzas = Success(pizzas),
                        cartCount = Success(newCount),
                        recyclerViewIndex = Success(recyclerViewIndex))
                }
            }
        }
    }

    fun setLoading() {
        setState {
            copy(pizzas = Loading())
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