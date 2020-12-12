package com.kingarmstring.dindinnexam.ui.payment.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.BaseMvRxFragment
import com.kingarmstring.dindinnexam.R

class OrdersFragment : BaseMvRxFragment(), View.OnTouchListener{

    var startHorizontalX: Float = 0.0f
    var endHorizontalX: Float = 0.0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_orders, container, false)
        view.setOnTouchListener(this)
        return view
    }

    override fun invalidate() {
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
                        findNavController().navigate(R.id.action_ordersFragment_to_cartFragment)
                    } else if (deltaX < -150){
                        findNavController().navigate(R.id.action_ordersFragment_to_informationFragment)
                    }
                }
            }
        }
        return true
    }
}