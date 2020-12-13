package com.kingarmstring.dindinnexam.ui.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.kingarmstring.dindinnexam.R

/**
 * people usually pass the context and instantiate layoutInflater here but passing the context is
 * against the clean code principles, less objects must not access higher objects and must be allowed
 * to communicate with them through contracts.
 */
class SliderPagerAdapter(private val layoutInflater: LayoutInflater) : PagerAdapter() {

    override fun getCount() = 3

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return when(position) {
            0 -> {
                val view = layoutInflater.inflate(R.layout.first_slide_layout,
                    container, false)
                container.addView(view)
                view
            }
            1-> {
                val view = layoutInflater.inflate(R.layout.second_slide_layout,
                    container, false)
                container.addView(view)
                view
            }
            else -> {
                val view = layoutInflater.inflate(R.layout.third_slide_layout,
                    container, false)
                container.addView(view)
                view
            }
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }
}