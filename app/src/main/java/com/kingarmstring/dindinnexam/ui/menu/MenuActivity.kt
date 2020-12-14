package com.kingarmstring.dindinnexam.ui.menu

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kingarmstring.dindinnexam.R
import com.kingarmstring.dindinnexam.ui.menu.contracts.MenuActivityContract
import com.kingarmstring.dindinnexam.ui.payment.PaymentActivity
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity(), MenuActivityContract {

    var bottomSheetInitialHeight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setPager()
        setPaymentClickHandler()
        setupBottomSheet()
    }


    private fun setPager() {
        slider_pager.adapter = SliderPagerAdapter(layoutInflater)
        slider_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                setActiveDot(position)
            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })
        setDotsClickListeners()
    }

    private fun setDotsClickListeners() {
        first_slide_dot.setOnClickListener {
            selectFirstDot()
            slider_pager.setCurrentItem(0, true)
        }
        second_slide_dot.setOnClickListener {
            selectSecondDot()
            slider_pager.setCurrentItem(1, true)
        }
        third_slide_dot.setOnClickListener {
            selectThirdDot()
            slider_pager.setCurrentItem(2, true)
        }
    }

    private fun setActiveDot(position: Int) {
        when (position) {
            0 ->  selectFirstDot()
            1 -> selectSecondDot()
            2 -> selectThirdDot()
        }
    }

    private fun selectThirdDot() {
        first_slide_dot.setImageResource(R.drawable.not_selected_slider_dot_drawable)
        second_slide_dot.setImageResource(R.drawable.not_selected_slider_dot_drawable)
        third_slide_dot.setImageResource(R.drawable.selected_slider_dot_drawable)
    }

    private fun selectSecondDot() {
        first_slide_dot.setImageResource(R.drawable.not_selected_slider_dot_drawable)
        second_slide_dot.setImageResource(R.drawable.selected_slider_dot_drawable)
        third_slide_dot.setImageResource(R.drawable.not_selected_slider_dot_drawable)
    }

    private fun selectFirstDot() {
        first_slide_dot.setImageResource(R.drawable.selected_slider_dot_drawable)
        second_slide_dot.setImageResource(R.drawable.not_selected_slider_dot_drawable)
        third_slide_dot.setImageResource(R.drawable.not_selected_slider_dot_drawable)
    }


    /**
     * Here we do two things:
     * 1. Set the initial height of the bottom sheet.
     * 2. Set the callback listener.
     */
    private fun setupBottomSheet() {
        setBottomSheetHeight()
        val bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        bottomSheetBehavior.peekHeight = bottomSheetInitialHeight
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {}

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset < .8) {
                    imgbutton.visibility = View.VISIBLE
                }else {
                    imgbutton.visibility = View.GONE
                }
                if (slideOffset > .95) view_pager_container.visibility = View.INVISIBLE
                else view_pager_container.visibility = View.VISIBLE

            }
        })
    }

    private fun setBottomSheetHeight() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val windowMetrics = windowManager.currentWindowMetrics
            val insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            bottomSheetInitialHeight = ((insets.bottom - insets.top) / 2)
        } else if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.R){
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            bottomSheetInitialHeight = displayMetrics.heightPixels
            bottomSheetInitialHeight /= 2
        }
    }

    private fun setPaymentClickHandler() {
        menu_fab_id.setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

    override fun handleButtonCount(count: Int) {
        cart_count_text.text = count.toString()
    }
}

/*
Notes:
1. Don't forget to add the number of items added to the cart on the FAB, easy task, yet easy to
forget :/
2. Remember to create dimes file and move all hard typed values to it.
3. Prevent fragments from navigating if the bottomsheet is collapsed and enable it if not.
4. Add offline mode, this mode will require me to ship some images in the drawable folder.
5. In the repository I will make filtration before returning any results but in real life app, I
will expect that there is an option to send a query text in the request to allow me to only fetch
MenuItems with for example type pizza, sushi or drink
6. Write a comment on the directory structure.
7. don't forget to dispose the observable.
 */

//need glide instance in the recyclerView to load images.
//need to listen to button clicks and touches through a callback.
//need to change the corners of the list to sharp edges when expanded
