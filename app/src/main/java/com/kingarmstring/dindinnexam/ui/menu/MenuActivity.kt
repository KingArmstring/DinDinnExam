package com.kingarmstring.dindinnexam.ui.menu

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kingarmstring.dindinnexam.R
import com.kingarmstring.dindinnexam.ui.menu.contracts.MenuActivityContract
import com.kingarmstring.dindinnexam.ui.payment.PaymentActivity
import com.kingarmstring.dindinnexam.utils.Constants.Companion.BOTTOM_SHEET_STATE
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_menu.*
import javax.inject.Inject

class MenuActivity : AppCompatActivity(), MenuActivityContract, HasSupportFragmentInjector {

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: MenuViewModel.Factory

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = supportFragmentInjector

    var bottomSheetInitialHeight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setFullScreen()
        setPager()
        setPaymentClickHandler()
        savedInstanceState?.let { state ->
            setupBottomSheet(state.getInt(BOTTOM_SHEET_STATE))
        } ?: setupBottomSheet()

    }

    private fun setFullScreen() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }
        else if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.R) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, // FLAG_FULLSCREEN is deprecated only
                WindowManager.LayoutParams.FLAG_FULLSCREEN  // starting from Android R but still valid before R
            )
        }
    }

    private fun setPager() {
        slider_pager.adapter = SliderPagerAdapter(layoutInflater)
        slider_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                setActiveDot(position)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

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
            0 -> selectFirstDot()
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
    private fun setupBottomSheet(state: Int = BottomSheetBehavior.STATE_COLLAPSED) {
        setBottomSheetHeight()
        val bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        if (state == BottomSheetBehavior.STATE_EXPANDED) {
            slide_indicator.visibility = View.GONE
            view_pager_container.visibility = View.INVISIBLE
        }
        bottomSheetBehavior.state = state
        bottomSheetBehavior.peekHeight = bottomSheetInitialHeight
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    slide_indicator.visibility = View.GONE
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset < .8) {
                    slide_indicator.visibility = View.VISIBLE
                } else if (slideOffset < .99){
                    slide_indicator.visibility = View.INVISIBLE
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(BOTTOM_SHEET_STATE, BottomSheetBehavior.from(bottom_sheet).state)
    }

}