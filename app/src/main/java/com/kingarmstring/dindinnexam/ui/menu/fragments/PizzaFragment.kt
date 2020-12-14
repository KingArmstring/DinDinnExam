package com.kingarmstring.dindinnexam.ui.menu.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.mvrx.*
import com.kingarmstring.dindinnexam.R
import com.kingarmstring.dindinnexam.models.MenuItem
import com.kingarmstring.dindinnexam.ui.menu.MenuAdapter
import com.kingarmstring.dindinnexam.ui.menu.MenuViewModel
import com.kingarmstring.dindinnexam.ui.menu.contracts.MenuActivityContract
import com.kingarmstring.dindinnexam.ui.menu.contracts.PizzaContract
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_pizza.*

class PizzaFragment : BaseMvRxFragment(), View.OnTouchListener, PizzaContract {

    var startHorizontalX: Float = 0.0f
    var endHorizontalX: Float = 0.0f
    private val pizzas: MutableList<MenuItem> = mutableListOf()
    private var addedPizzas: List<MenuItem> = listOf()

    private val menuViewModel: MenuViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pizza, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view_menu.setOnTouchListener(this)
        setupPizzasList()
        menuViewModel.getPizzas()
    }

    override fun invalidate(): Unit = withState(menuViewModel) { menuState ->
            when(menuState.pizzas) {
                is Loading -> {
                // maybe show progressBar but did not see it in the design, but that's what I will
                // be doing, showing a progressBar.
                }
                is Success -> {
                    //hide progressBar if any
                    //populate the UI with data(recyclerView in this case)
                    val menuItems: List<MenuItem> = menuState.pizzas.invoke()
                    pizzas.clear()
                    pizzas.addAll(menuItems)
                    recycler_view_menu.adapter?.notifyDataSetChanged()
                    menuState.addedPizzas.invoke()?.let {
                        addedPizzas = it
                    }
                    (activity as MenuActivityContract).handleButtonCount(addedPizzas.size)
                }
                is Fail -> {
                    //hide progressBar if any
                    //show error message in the form agreed on by the design team(dialog/toast/none,
                    //none which means not showing the user any kind of error usually used in cases
                    //like when auto-login fails, giving the used an error telling him/her that
                    //auto login failed obviously means nothing, just let him/her login again).
                }
                is Uninitialized -> {
                    //do nothing!
                }
            }
        }

    private fun setupPizzasList() {
        recycler_view_menu.layoutManager = LinearLayoutManager(requireContext())
        recycler_view_menu.adapter = MenuAdapter(pizzas, this)
    }


    override fun onTouch(p0: View?, event: MotionEvent?): Boolean {
        event?.let {
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startHorizontalX = event.x
                }
                MotionEvent.ACTION_UP -> {
                    endHorizontalX = event.x
                    val deltaX = endHorizontalX - startHorizontalX
                    if (deltaX > 150) {
                        //no need to handle this case because this is the most left fragment
                    } else if (deltaX < -150){
                        findNavController().navigate(R.id.action_pizzaFragment_to_sushiFragment)
                    }
                }
            }
        }
        return false
    }

    override fun addToCartButtonClickCallback(pizza: MenuItem) {
        menuViewModel.addItemToCart(pizza)
    }
}