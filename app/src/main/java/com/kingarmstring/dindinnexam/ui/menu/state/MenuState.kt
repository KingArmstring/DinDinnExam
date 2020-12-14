package com.kingarmstring.dindinnexam.ui.menu.state

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.kingarmstring.dindinnexam.models.MenuItem

data class MenuState(
    val pizzas: Async<List<MenuItem>> = Uninitialized,
    val addedPizzas: Async<MutableList<MenuItem>> = Uninitialized
) : MvRxState