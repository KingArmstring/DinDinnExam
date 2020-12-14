package com.kingarmstring.dindinnexam.ui.menu


import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.kingarmstring.dindinnexam.appscope.DinDinnExamApp
import com.kingarmstring.dindinnexam.models.MenuItem
import com.kingarmstring.dindinnexam.repository.MenuRepository
import com.kingarmstring.dindinnexam.ui.menu.state.MenuState

class MenuViewModel(
    state: MenuState,
    private val menuRepository: MenuRepository
) : BaseMvRxViewModel<MenuState>(state, debugMode = true){

    init {
        setState {
            copy(pizzas = Loading())
        }
    }

    fun getPizzas() = menuRepository.getPizzas()
            .execute { pizzasState ->
                copy(pizzas = pizzasState)
            }

    fun getSushi() = menuRepository.getSushi()
        .execute { pizzasState ->
            copy(pizzas = pizzasState)
        }

    fun getDrinks() = menuRepository.getDrinks()
        .execute { pizzasState ->
            copy(pizzas = pizzasState)
        }

    fun addItemToCart(menuItem: MenuItem) {
        //add this to the state
        withState { state ->
            val current = state.addedPizzas
            current.invoke()?.add(menuItem)
            state.copy(addedPizzas = current)
        }
    }


    companion object : MvRxViewModelFactory<MenuViewModel, MenuState> {
        override fun create(viewModelContext: ViewModelContext,
                            state: MenuState): MenuViewModel {
            val menuRepository = viewModelContext.app<DinDinnExamApp>().watchlistRepository
            return MenuViewModel(state, menuRepository)
        }
    }
}