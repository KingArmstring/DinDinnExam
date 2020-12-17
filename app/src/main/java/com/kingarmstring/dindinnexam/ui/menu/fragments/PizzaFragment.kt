package com.kingarmstring.dindinnexam.ui.menu.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.mvrx.*
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions

import com.kingarmstring.dindinnexam.R
import com.kingarmstring.dindinnexam.models.MenuItem
import com.kingarmstring.dindinnexam.ui.menu.MenuAdapter
import com.kingarmstring.dindinnexam.ui.menu.MenuViewModel
import com.kingarmstring.dindinnexam.ui.menu.contracts.MenuActivityContract
import com.kingarmstring.dindinnexam.ui.menu.contracts.PizzaContract
import com.kingarmstring.dindinnexam.utils.SpacingItemDecorator
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.fragment_pizza.*
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import javax.inject.Inject

class PizzaFragment : BaseMvRxFragment(), View.OnTouchListener, PizzaContract {

    var startHorizontalX: Float = 0.0f
    var endHorizontalX: Float = 0.0f


    @Inject
    lateinit var glideRequestOptions: RequestOptions
    
    lateinit var glideRequestManager: RequestManager

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
        setupGlide()
        setupPizzasList()
        menuViewModel.getPizzas(getMenuItems())
    }

    fun getMenuItems(): String {
        val inputStream = activity?.assets?.open("menu.json")
        val builder = StringBuilder()
        val reader = BufferedReader(InputStreamReader(inputStream))
        var str = reader.readLine()
        while (str != null) {
            builder.append(str)
            str = reader.readLine()
        }
        val jsonArray = JSONArray(builder.toString())
        return jsonArray.toString()
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
                        }else if (newState == RecyclerView.SCROLL_STATE_IDLE ||
                            newState == RecyclerView.SCROLL_STATE_SETTLING) {
                            recycler_view_menu.setOnTouchListener(this@PizzaFragment)
                        }
                    }

                }
            })
            adapter = MenuAdapter( this, glideRequestManager)
            recycler_view_menu.layoutManager = LinearLayoutManager(requireContext())
            recycler_view_menu.addItemDecoration(SpacingItemDecorator())
            recycler_view_menu.adapter = adapter
        }
    }

    private fun setupGlide() {
//        glideRequestOptions = RequestOptions
//            .placeholderOf(R.drawable.default_image)
//            .error(R.drawable.default_image)
//            .transform(FitCenter(),
//                GranularRoundedCorners(
//                    28f,
//                    28f,
//                    0f,
//                    0f
//                ))

        glideRequestManager = Glide.with(requireContext())
            .setDefaultRequestOptions(glideRequestOptions)
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

    override fun addToCartButtonClickCallback(pizza: MenuItem) {
        menuViewModel.addItemToCart(pizza, requireContext())
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