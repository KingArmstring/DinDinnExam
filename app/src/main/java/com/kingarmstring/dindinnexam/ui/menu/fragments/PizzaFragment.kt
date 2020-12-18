package com.kingarmstring.dindinnexam.ui.menu.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.mvrx.*
import com.bumptech.glide.RequestManager

import com.kingarmstring.dindinnexam.R
import com.kingarmstring.dindinnexam.models.MenuItem
import com.kingarmstring.dindinnexam.ui.menu.adapters.MenuAdapter
import com.kingarmstring.dindinnexam.ui.menu.MenuViewModel
import com.kingarmstring.dindinnexam.ui.menu.contracts.MenuActivityContract
import com.kingarmstring.dindinnexam.ui.menu.contracts.PizzaContract
import com.kingarmstring.dindinnexam.utils.SpacingItemDecorator
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.fragment_pizza.*
import javax.inject.Inject
import kotlin.math.abs

class PizzaFragment : BaseMvRxFragment(), View.OnTouchListener, PizzaContract {

    //these two variables are fo handling swiping via touch listener.
    var startHorizontalX: Float = 0.0f
    var endHorizontalX: Float = 0.0f

    @Inject
    lateinit var glideRequestManager: RequestManager

    @Inject
    lateinit var viewModelFactory: MenuViewModel.Factory

    private val menuViewModel: MenuViewModel by activityViewModel()

    lateinit var adapter: MenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pizza, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPizzasList()
        // if savedInstanceState is not null, this means that it saved inside the state and method invalidate will be called and set the view automatically
        if(savedInstanceState == null) menuViewModel.getPizzas()
    }

    override fun invalidate(): Unit = withState(menuViewModel) { menuState ->
        when(menuState.pizzas) {
            is Loading -> {
                // maybe show progressBar but did not see it in the design, but that's what I will
                // be doing, showing a progressBar.
                pizza_progress_bar.visibility = View.VISIBLE
            }
            is Success -> {
                //hide progressBar if any
                //populate the UI with data(recyclerView in this case)
                pizza_progress_bar.visibility = View.GONE
                if (adapter.differ.currentList.size == 0)
                    adapter.notifyDataSetChanged()
                adapter.submitList(menuState.pizzas.invoke())
                menuState.recyclerViewIndex.invoke()?.let {
                    if (abs(it-(recycler_view_menu.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()) > 1)
                        recycler_view_menu.layoutManager!!.scrollToPosition(it)
                }
            }
            is Fail -> {
                //hide progressBar if any
                //show error message in the form agreed on by the design team(dialog/toast/none,
                //none which means not showing the user any kind of error usually used in cases
                //like when auto-login fails, giving the used an error telling him/her that
                //auto login failed obviously means nothing, just let him/her login again).
                pizza_progress_bar.visibility = View.GONE
                Toast.makeText(requireContext(), "Error fetching menu items", Toast.LENGTH_SHORT).show()
            }else -> {
                // do nothing, this else clause just to satisfy lint xD
            }
        }

        if (menuState.cartCount is Success) {
            (activity as MenuActivityContract).handleButtonCount(menuState.cartCount.invoke())
        }
    }

    private fun setupPizzasList() {
        if (!::adapter.isInitialized) {
            recycler_view_menu.setOnTouchListener(this)
            recycler_view_menu.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    recycler_view_menu?.let {
                        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                            recycler_view_menu.setOnTouchListener(null)
                        } else if (newState == RecyclerView.SCROLL_STATE_IDLE ||
                            newState == RecyclerView.SCROLL_STATE_SETTLING
                        ) {
                            recycler_view_menu.setOnTouchListener(this@PizzaFragment)
                        }
                    }
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        menuViewModel.setRecyclerViewIndex(
                            (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                        )
                    }
                }
            })
            adapter = MenuAdapter( this, glideRequestManager)
            recycler_view_menu.layoutManager = LinearLayoutManager(requireContext())
            recycler_view_menu.addItemDecoration(SpacingItemDecorator())
            recycler_view_menu.adapter = adapter
        }
    }


    override fun addToCartButtonClickCallback(pizza: MenuItem) {
        menuViewModel.addItemToCart(pizza, requireContext())
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        event?.let {
            when(event.action) {
                MotionEvent.ACTION_SCROLL -> {
                    return true
                }
                MotionEvent.ACTION_DOWN -> {
                    view?.performClick()
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

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}
//6. When hiding the three dots at the top of the bottom sheet, try to make it smooth.
//7. Add text to be shown when Fail
//8. Collect all constants in one file.
//9. Add methods comments.
//10. Change the location of the ProgressBar to the center when the bottom sheet is expanded.
//11. Create the network layer by adding the event and network wrapper around the returned response.
//12. Add Error icon in the same place of the progressBar and show it when Fail, also make this view move to the center of the screen when the user expand the bottom sheet.
//13. Images should come from server.
//14. Set the limit of adding items to cart to 99.
//15. Move the progressbar to the center of the screen when the bottomsheet gets expanded and top when collapsed.
//16. Add new menu item black button is not complete.
//17. Handle process death.
//18. Use shimmer facebook.
//19. Remove kotlin-android-extensions plugin
//20. Replace the header item by an empty MenuItem at the zeroth position.
//21. Remove recyclerview ripples.
//22. Make it work offline.
//23. Replace all !! with ?.let

/*
Now:
0. Move all dependencies to
1. Save config changes.
2. Apply filter to get only pizzas
3. Fill other lists.
 */

/*
Later:
1. Process death.
 */