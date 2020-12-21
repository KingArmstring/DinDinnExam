package com.kingarmstring.dindinnexam.ui.payment.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.mvrx.*
import com.bumptech.glide.RequestManager
import com.kingarmstring.dindinnexam.R
import com.kingarmstring.dindinnexam.models.MenuItem
import com.kingarmstring.dindinnexam.ui.payment.PaymentViewModel
import com.kingarmstring.dindinnexam.ui.payment.adapters.CartAdapter
import com.kingarmstring.dindinnexam.ui.payment.contracts.PaymentContract
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_cart.*
import javax.inject.Inject

class CartFragment : BaseMvRxFragment(), View.OnTouchListener, PaymentContract {

    var startHorizontalX: Float = 0.0f
    var endHorizontalX: Float = 0.0f

    @Inject
    lateinit var glideRequestManager: RequestManager

    @Inject
    lateinit var viewModelFactory: PaymentViewModel.Factory

    private val paymentViewModel: PaymentViewModel by activityViewModel()

    lateinit var adapter: CartAdapter

    val list: MutableList<MenuItem> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        view.setOnTouchListener(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCartItemsList()
        if (savedInstanceState == null) paymentViewModel.getCartItems()
    }

    override fun invalidate() {
        withState(paymentViewModel) { state ->
            when(state.cartItems) {
                is Loading -> {
                    payment_progress_bar.visibility = View.VISIBLE
                }
                is Success -> {
                    if(state.itemRemoved is Uninitialized) {
                        payment_progress_bar.visibility = View.GONE
                        list.clear()
                        list.add(MenuItem()) // adding extra item for the header
                        list.addAll(state.cartItems.invoke())
                        list.add(MenuItem()) // adding extra item for the footer
                        adapter.notifyDataSetChanged()
                        recycler_view_cart.layoutManager?.scrollToPosition(0)
                    }
                }
                is Fail -> {
                    payment_progress_bar.visibility = View.GONE
                }
                is Uninitialized -> { }
            }

            if (state.itemRemoved is Success) {
                payment_progress_bar.visibility = View.GONE
                state.itemRemoved.invoke().getContentIfNotHandled()?.let { removedItemPosition ->
                    list.removeAt(removedItemPosition)
                    adapter.notifyItemRemoved(removedItemPosition+1)
                    adapter.notifyItemRangeChanged(removedItemPosition+1, list.size)
                }
            }
        }
    }

    private fun setCartItemsList() {
        adapter = CartAdapter(this, glideRequestManager, list)
        recycler_view_cart.layoutManager = LinearLayoutManager(requireContext())
        recycler_view_cart.adapter = adapter
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
                        //no need to handle this because this is the most left fragment.
                    } else {
                        findNavController().navigate(R.id.action_cartFragment_to_ordersFragment)
                    }
                }
            }
        }
        return true
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun removeItemFromCart(index: Int) {
        paymentViewModel.removeItemFromCart(index)
        //NOW after remove the item, I should just call method notifyItemRemoved on the callback of the state
    }
}