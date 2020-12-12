package com.kingarmstring.dindinnexam.ui.menu.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.BaseMvRxFragment
import com.kingarmstring.dindinnexam.R

class SushiFragment : BaseMvRxFragment(), View.OnTouchListener {

    var startHorizontalX: Float = 0.0f
    var endHorizontalX: Float = 0.0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sushi, container, false)
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
                    Log.d("KingArmstring", "onTouch: $deltaX")
                    if (deltaX > 150) {
                        findNavController().navigate(R.id.action_sushiFragment_to_pizzaFragment)
                    } else if (deltaX < -150){
                        findNavController().navigate(R.id.action_sushiFragment_to_drinksFragment)
                    }
                }
            }
        }
        return true
    }
}