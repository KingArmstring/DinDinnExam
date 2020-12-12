package com.kingarmstring.dindinnexam.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.BaseMvRxFragment
import com.kingarmstring.dindinnexam.R
import kotlin.math.abs

class DrinksFragment : BaseMvRxFragment(), View.OnTouchListener{

    var startHorizontalX: Float = 0.0f
    var endHorizontalX: Float = 0.0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_drinks, container, false)
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
                        findNavController().navigate(R.id.action_drinksFragment_to_sushiFragment)
                    } else {
                        //no need to handle this because this is the most right fragment.
                    }
                }
            }
        }
        return true
    }
}