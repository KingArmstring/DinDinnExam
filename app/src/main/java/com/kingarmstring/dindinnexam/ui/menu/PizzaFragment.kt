package com.kingarmstring.dindinnexam.ui.menu

import android.content.Context
import android.os.Bundle
import android.util.Log
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

class PizzaFragment : BaseMvRxFragment(), View.OnTouchListener{

    var startHorizontalX: Float = 0.0f
    var endHorizontalX: Float = 0.0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pizza, container, false)
        view.setOnTouchListener(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("KingArmstring", "onViewCreated!!")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("KingArmstring", "onAttached")
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
                        //no need to handle this case because this is the most left fragment
                    } else if (deltaX < -150){
                        findNavController().navigate(R.id.action_pizzaFragment_to_sushiFragment)
                    }
                }
            }
        }
        return true
    }
}